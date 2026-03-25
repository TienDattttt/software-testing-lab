package com.apitesting.tests.lab2;

import com.apitesting.base.ApiBaseTest;
import com.apitesting.models.CreateUserRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 2 - CRUD User Operations")
public class CrudUserTest extends ApiBaseTest {

    @Test(description = "POST /users creates user with 201")
    @Story("Create a new user")
    public void testCreateUser() {
        Response response = given(requestSpec)
                .body(new CreateUserRequest("Nguyen Van A", "QA Engineer"))
                .when()
                .post("/users");

        response.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo("Nguyen Van A"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());

        String createdId = response.jsonPath().getString("id");
        System.out.println("Created user ID: " + createdId);
    }

    @Test(description = "PUT /users/2 updates user with 200")
    @Story("Update an existing user")
    public void testUpdateUser() {
        given(requestSpec)
                .body(new CreateUserRequest("Tran Thi B", "Senior QA"))
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Senior QA"))
                .body("updatedAt", notNullValue());
    }

    @Test(description = "PATCH /users/2 partially updates user")
    @Story("Patch user job only")
    public void testPatchUser() {
        given(requestSpec)
                .body(Collections.singletonMap("job", "Lead QA"))
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Lead QA"))
                .body("updatedAt", notNullValue());
    }

    @Test(description = "DELETE /users/2 returns 204 no content")
    @Story("Delete a user")
    public void testDeleteUser() {
        Response response = given(requestSpec)
                .when()
                .delete("/users/2");

        response.then().statusCode(204);
        Assert.assertTrue(response.body().asString().isEmpty(), "Response body should be empty");
    }

    @Test(description = "POST create user then verify id exists")
    @Story("Create user and verify assigned id")
    public void testCreateThenGetUser() {
        Response createResponse = given(requestSpec)
                .body(new CreateUserRequest("Le Van C", "Tester"))
                .when()
                .post("/users");

        createResponse.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo("Le Van C"))
                .body("id", notNullValue());

        String createdId = createResponse.jsonPath().getString("id");
        System.out.println("Created user ID: " + createdId);

        given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200);

        // reqres.in is a mock API; GET by new id returns 404, but we verify id was assigned by server.
        Assert.assertNotNull(createdId, "Created id should not be null");
        Assert.assertFalse(createdId.isEmpty(), "Created id should not be empty");
    }
}
