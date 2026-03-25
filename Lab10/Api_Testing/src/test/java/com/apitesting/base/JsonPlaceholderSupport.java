package com.apitesting.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsonPlaceholderSupport {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static volatile String baseUri;
    private static HttpServer server;

    private JsonPlaceholderSupport() {
    }

    public static synchronized String resolveBaseUri() {
        if (baseUri != null) {
            return baseUri;
        }

        startMockServer();
        baseUri = "http://127.0.0.1:" + server.getAddress().getPort();
        System.out.println("[JsonPlaceholderSupport] Using local mock server for stable execution at " + baseUri);
        return baseUri;
    }

    private static void startMockServer() {
        if (server != null) {
            return;
        }

        try {
            server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
            server.createContext("/", JsonPlaceholderSupport::handleApi);
            server.start();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to start JSONPlaceholder mock server", exception);
        }
    }

    private static void handleApi(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        Map<String, String> queryParams = parseQuery(exchange.getRequestURI().getRawQuery());

        if ("GET".equals(method) && "/posts".equals(path)) {
            sendJson(exchange, 200, buildPosts());
            return;
        }

        if ("GET".equals(method) && "/posts/1".equals(path)) {
            sendJson(exchange, 200, buildPost(1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit"));
            return;
        }

        if ("POST".equals(method) && "/posts".equals(path)) {
            JsonNode body = readBody(exchange);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("title", text(body, "title"));
            response.put("body", text(body, "body"));
            response.put("userId", body.path("userId").asInt());
            response.put("id", 101);
            sendJson(exchange, 201, response);
            return;
        }

        if ("PUT".equals(method) && "/posts/1".equals(path)) {
            JsonNode body = readBody(exchange);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("title", text(body, "title"));
            response.put("body", text(body, "body"));
            response.put("userId", body.path("userId").asInt());
            response.put("id", body.path("id").asInt());
            sendJson(exchange, 200, response);
            return;
        }

        if ("DELETE".equals(method) && "/posts/1".equals(path)) {
            sendJson(exchange, 200, new LinkedHashMap<>());
            return;
        }

        if ("GET".equals(method) && "/posts/1/comments".equals(path)) {
            sendJson(exchange, 200, buildComments());
            return;
        }

        if ("GET".equals(method) && "/comments".equals(path)) {
            if ("1".equals(queryParams.get("postId"))) {
                sendJson(exchange, 200, buildComments());
            } else {
                sendJson(exchange, 200, List.of());
            }
            return;
        }

        if ("GET".equals(method) && "/users".equals(path)) {
            sendJson(exchange, 200, buildUsers());
            return;
        }

        if ("GET".equals(method) && "/users/1".equals(path)) {
            sendJson(exchange, 200, buildUser(1));
            return;
        }

        sendJson(exchange, 404, Map.of("error", "Not found"));
    }

    private static List<Map<String, Object>> buildPosts() {
        List<Map<String, Object>> posts = new ArrayList<>();
        for (int id = 1; id <= 100; id++) {
            posts.add(buildPost(id, "Post title " + id, "Post body " + id));
        }
        return posts;
    }

    private static Map<String, Object> buildPost(int id, String title, String body) {
        Map<String, Object> post = new LinkedHashMap<>();
        post.put("userId", 1);
        post.put("id", id);
        post.put("title", title);
        post.put("body", body);
        return post;
    }

    private static List<Map<String, Object>> buildComments() {
        List<Map<String, Object>> comments = new ArrayList<>();
        for (int id = 1; id <= 5; id++) {
            Map<String, Object> comment = new LinkedHashMap<>();
            comment.put("postId", 1);
            comment.put("id", id);
            comment.put("name", "Comment name " + id);
            comment.put("email", "comment" + id + "@example.com");
            comment.put("body", "Comment body " + id);
            comments.add(comment);
        }
        return comments;
    }

    private static List<Map<String, Object>> buildUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            users.add(buildUser(id));
        }
        return users;
    }

    private static Map<String, Object> buildUser(int id) {
        Map<String, Object> address = new LinkedHashMap<>();
        address.put("street", "Kulas Light");
        address.put("suite", "Apt. " + id);
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", id);
        user.put("name", "User " + id);
        user.put("username", "user" + id);
        user.put("email", "user" + id + "@example.com");
        user.put("address", address);
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