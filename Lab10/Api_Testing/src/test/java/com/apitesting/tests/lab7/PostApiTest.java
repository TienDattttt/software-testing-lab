package com.apitesting.tests.lab7;

import com.apitesting.base.JsonPlaceholderBaseTest;
import com.apitesting.models.CreatePostRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 7 - JSONPlaceholder Posts")
public class PostApiTest extends JsonPlaceholderBaseTest {

    @Test(description = "GET /posts returns 100 posts within SLA")
    @Story("Get all posts")
    public void testGetAllPosts() {
        given(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test(description = "GET /posts/1 returns one post and matches schema")
    @Story("Get single post")
    public void testGetSinglePost() {
        given(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyString()))
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test(description = "POST /posts creates a new post")
    @Story("Create a post")
    public void testCreatePost() {
        given(requestSpec)
                .body(new CreatePostRequest("Test Title", "Test Body", 1))
                .when()
                .post("/posts")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("title", equalTo("Test Title"))
                .body("id", notNullValue());
    }

    @Test(description = "PUT /posts/1 updates an existing post")
    @Story("Update a post")
    public void testUpdatePost() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Updated Title");
        body.put("body", "Updated Body");
        body.put("userId", 1);
        body.put("id", 1);

        given(requestSpec)
                .body(body)
                .when()
                .put("/posts/1")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("title", equalTo("Updated Title"));
    }

    @Test(description = "DELETE /posts/1 returns 200 within SLA")
    @Story("Delete a post")
    public void testDeletePost() {
        given(requestSpec)
                .when()
                .delete("/posts/1")
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }
}
