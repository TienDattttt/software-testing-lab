package com.apitesting.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReqresSupport {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static volatile String baseUri;
    private static HttpServer server;

    private ReqresSupport() {
    }

    public static synchronized String resolveBaseUri() {
        if (baseUri != null) {
            return baseUri;
        }

        if (isLiveAvailable()) {
            baseUri = "https://reqres.in";
            System.out.println("[ReqresSupport] Using live ReqRes endpoint.");
            return baseUri;
        }

        startMockServer();
        baseUri = "http://127.0.0.1:" + server.getAddress().getPort();
        System.out.println("[ReqresSupport] Live ReqRes unavailable, using local mock server at " + baseUri);
        return baseUri;
    }

    private static boolean isLiveAvailable() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://reqres.in/api/users?page=2").openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(2500);
            connection.setReadTimeout(2500);
            int status = connection.getResponseCode();
            String contentType = connection.getContentType();
            return status == 200 && contentType != null && contentType.contains("application/json");
        } catch (Exception ignored) {
            return false;
        }
    }

    private static void startMockServer() {
        if (server != null) {
            return;
        }

        try {
            server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
            server.createContext("/api", ReqresSupport::handleApi);
            server.start();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to start ReqRes mock server", exception);
        }
    }

    private static void handleApi(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath().substring("/api".length());
        Map<String, String> queryParams = parseQuery(exchange.getRequestURI().getRawQuery());

        if ("GET".equals(method) && "/users".equals(path)) {
            int page = Integer.parseInt(queryParams.getOrDefault("page", "1"));
            sendJson(exchange, 200, buildUserListResponse(page));
            return;
        }

        if ("GET".equals(method) && path.startsWith("/users/")) {
            int userId = Integer.parseInt(path.substring("/users/".length()));
            if (userId == 9999) {
                sendJson(exchange, 404, new LinkedHashMap<>());
                return;
            }
            sendJson(exchange, 200, buildSingleUserResponse(userId));
            return;
        }

        if ("POST".equals(method) && "/login".equals(path)) {
            JsonNode body = readBody(exchange);
            String email = text(body, "email");
            String password = text(body, "password");
            if (isBlank(email)) {
                sendJson(exchange, 400, Map.of("error", "Missing email or username"));
            } else if (isBlank(password)) {
                sendJson(exchange, 400, Map.of("error", "Missing password"));
            } else if ("eve.holt@reqres.in".equals(email) && "cityslicka".equals(password)) {
                sendJson(exchange, 200, Map.of("token", "mock-reqres-token"));
            } else {
                sendJson(exchange, 400, Map.of("error", "user not found"));
            }
            return;
        }

        if ("POST".equals(method) && "/register".equals(path)) {
            JsonNode body = readBody(exchange);
            String email = text(body, "email");
            String password = text(body, "password");
            if (isBlank(password)) {
                sendJson(exchange, 400, Map.of("error", "Missing password"));
            } else if ("eve.holt@reqres.in".equals(email) && "pistol".equals(password)) {
                sendJson(exchange, 200, Map.of("id", 4, "token", "mock-register-token"));
            } else {
                sendJson(exchange, 400, Map.of("error", "Note: Only defined users succeed in this mock"));
            }
            return;
        }

        if ("POST".equals(method) && "/users".equals(path)) {
            JsonNode body = readBody(exchange);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("name", text(body, "name"));
            response.put("job", text(body, "job"));
            response.put("id", "1001");
            response.put("createdAt", Instant.now().toString());
            sendJson(exchange, 201, response);
            return;
        }

        if (("PUT".equals(method) || "PATCH".equals(method)) && "/users/2".equals(path)) {
            JsonNode body = readBody(exchange);
            Map<String, Object> response = new LinkedHashMap<>();
            if (!isBlank(text(body, "name"))) {
                response.put("name", text(body, "name"));
            }
            if (!isBlank(text(body, "job"))) {
                response.put("job", text(body, "job"));
            }
            response.put("updatedAt", Instant.now().toString());
            sendJson(exchange, 200, response);
            return;
        }

        if ("DELETE".equals(method) && "/users/2".equals(path)) {
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return;
        }

        sendJson(exchange, 404, Map.of("error", "Not found"));
    }

    private static Map<String, Object> buildUserListResponse(int page) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page", page);
        response.put("per_page", 6);
        response.put("total", 12);
        response.put("total_pages", 2);
        response.put("data", buildUsersForPage(page));
        response.put("support", Map.of(
                "url", "https://reqres.in/#support-heading",
                "text", "To keep ReqRes free, contributions towards server costs are appreciated!"
        ));
        return response;
    }

    private static List<Map<String, Object>> buildUsersForPage(int page) {
        List<Map<String, Object>> users = new ArrayList<>();
        int start = page == 2 ? 7 : 1;
        for (int id = start; id < start + 6; id++) {
            users.add(buildUser(id));
        }
        return users;
    }

    private static Map<String, Object> buildSingleUserResponse(int id) {
        return Map.of(
                "data", buildUser(id),
                "support", Map.of(
                        "url", "https://reqres.in/#support-heading",
                        "text", "To keep ReqRes free, contributions towards server costs are appreciated!"
                )
        );
    }

    private static Map<String, Object> buildUser(int id) {
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", id);
        user.put("email", "user" + id + "@reqres.in");
        user.put("first_name", "User" + id);
        user.put("last_name", "Mock" + id);
        user.put("avatar", "https://reqres.in/img/faces/" + id + "-image.jpg");
        return user;
    }

    private static JsonNode readBody(HttpExchange exchange) throws IOException {
        try (InputStream inputStream = exchange.getRequestBody()) {
            byte[] bytes = inputStream.readAllBytes();
            if (bytes.length == 0) {
                return MAPPER.createObjectNode();
            }
            return MAPPER.readTree(bytes);
        }
    }

    private static String text(JsonNode node, String field) {
        JsonNode child = node.get(field);
        return child == null || child.isNull() ? null : child.asText();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static Map<String, String> parseQuery(String rawQuery) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isEmpty()) {
            return queryParams;
        }

        for (String pair : rawQuery.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = parts[0];
            String value = parts.length > 1 ? parts[1] : "";
            queryParams.put(key, value);
        }
        return queryParams;
    }

    private static void sendJson(HttpExchange exchange, int statusCode, Object body) throws IOException {
        byte[] json = MAPPER.writeValueAsString(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, json.length);
        exchange.getResponseBody().write(json);
        exchange.close();
    }
}