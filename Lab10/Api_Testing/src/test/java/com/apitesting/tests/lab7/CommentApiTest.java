package com.apitesting.tests.lab7;

import com.apitesting.base.JsonPlaceholderBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 7 - JSONPlaceholder Comments")
public class CommentApiTest extends JsonPlaceholderBaseTest {

    @Test(description = "GET /posts/1/comments returns 5 comments for post 1")
    @Story("Get comments by nested endpoint")
    public void testGetCommentsForPost1() {
        given(requestSpec)
                .when()
                .get("/posts/1/comments")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", equalTo(5))
                .body("postId", everyItem(equalTo(1)))
                .body("id", everyItem(notNullValue()))
                .body("email", everyItem(containsString("@")))
                .body("name", everyItem(not(emptyString())))
                .body("body", everyItem(not(emptyString())));
    }

    @Test(description = "GET /comments?postId=1 returns filtered comments")
    @Story("Get comments by query parameter")
    public void testGetCommentsByQueryParam() {
        given(requestSpec)
                .queryParam("postId", 1)
                .when()
                .get("/comments")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", equalTo(5))
                .body("[0].postId", equalTo(1));
    }
}
