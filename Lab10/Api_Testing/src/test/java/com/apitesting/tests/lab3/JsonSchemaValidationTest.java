package com.apitesting.tests.lab3;

import com.apitesting.base.ApiBaseTest;
import com.apitesting.models.CreateUserRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Lab 10 - API Testing")
@Feature("Lab 3 - JSON Schema Validation")
public class JsonSchemaValidationTest extends ApiBaseTest {

    @Test(description = "GET /users response matches user-list-schema")
    @Story("Validate user list schema")
    public void testUserListSchema() {
        given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"));
    }

    @Test(description = "GET /users/2 matches user-schema")
    @Story("Validate single user schema")
    public void testSingleUserSchema() {
        given(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test(description = "POST /users response matches create-user-schema")
    @Story("Validate create user schema")
    public void testCreateUserSchema() {
        given(requestSpec)
                .body(new CreateUserRequest("Schema Test User", "Test Job"))
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }
}
