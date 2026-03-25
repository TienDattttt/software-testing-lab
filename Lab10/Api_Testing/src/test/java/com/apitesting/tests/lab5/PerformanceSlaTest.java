package com.apitesting.tests.lab5;

import com.apitesting.base.ApiBaseTest;
import com.apitesting.models.CreateUserRequest;
import com.apitesting.models.LoginRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

@Epic("Lab 10 - API Testing")
@Feature("Lab 5 - Performance and SLA")
public class PerformanceSlaTest extends ApiBaseTest {

    @DataProvider(name = "slaEndpoints")
    public Object[][] slaEndpoints() {
        return new Object[][]{
                {"GET", "/users", 200, 2000L},
                {"GET", "/users/2", 200, 1500L},
                {"POST", "/users", 201, 3000L},
                {"POST", "/login", 200, 2000L},
                {"DELETE", "/users/2", 204, 1000L},
        };
    }

    @Test(dataProvider = "slaEndpoints", description = "SLA monitoring for all critical endpoints")
    @Story("Measure SLA for critical API endpoints")
    public void testSlaForEndpoints(String method, String endpoint, int expectedStatus, long maxMs) {
        RequestSpecification spec = given(requestSpec);

        if (method.equals("POST") && endpoint.contains("users")) {
            spec = spec.body(new CreateUserRequest("SLA Test", "Tester"));
        } else if (method.equals("POST") && endpoint.contains("login")) {
            spec = spec.body(new LoginRequest("eve.holt@reqres.in", "cityslicka"));
        }

        long start = System.currentTimeMillis();

        Response response;
        switch (method) {
            case "GET":
                response = spec.when().get(endpoint);
                break;
            case "POST":
                response = spec.when().post(endpoint);
                break;
            case "DELETE":
                response = spec.when().delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unknown method: " + method);
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[SLA] %s %s -> %dms (limit: %dms)%n", method, endpoint, elapsed, maxMs);

        response.then()
                .statusCode(expectedStatus)
                .time(lessThan(maxMs));
    }

    @Test(description = "Stress: call GET /users/1 ten times and log avg/min/max")
    @Story("Measure average response time under repeated load")
    public void testStressResponseTime() {
        long[] times = new long[10];

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            given(requestSpec)
                    .when()
                    .get("/users/1")
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
            times[i] = System.currentTimeMillis() - start;
        }

        long min = Long.MAX_VALUE;
        long max = 0L;
        long sum = 0L;
        for (long time : times) {
            sum += time;
            if (time < min) {
                min = time;
            }
            if (time > max) {
                max = time;
            }
        }

        double avg = (double) sum / 10;
        System.out.printf("[Stress] GET /users/1 x10 -> avg=%.1fms, min=%dms, max=%dms%n", avg, min, max);
        Assert.assertTrue(avg < 3000, "Average response time " + avg + "ms exceeds SLA 3000ms");
    }
}
