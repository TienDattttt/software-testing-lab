package com.apitesting.tests.lab7;

import com.apitesting.base.JsonPlaceholderBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 7 - JSONPlaceholder Users")
public class UserApiTest extends JsonPlaceholderBaseTest {

    @Test(description = "GET /users returns 10 users with valid fields")
    @Story("Get all users")
    public void testGetAllUsers() {
        given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("id", everyItem(notNullValue()))
                .body("name", everyItem(not(emptyString())))
                .body("email", everyItem(containsString("@")));
    }

    @Test(description = "GET /users/1 matches schema and contains address data")
    @Story("Get single user with schema validation")
    public void testGetSingleUserWithSchemaValidation() {
        given(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-jsonplaceholder-schema.json"))
                .body("id", equalTo(1))
                .body("address.city", notNullValue())
                .body("address.zipcode", not(emptyString()));
    }
}
