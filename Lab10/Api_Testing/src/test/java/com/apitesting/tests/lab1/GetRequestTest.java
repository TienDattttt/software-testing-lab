package com.apitesting.tests.lab1;

import com.apitesting.base.ApiBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 1 - GET Request Basics")
public class GetRequestTest extends ApiBaseTest {

    @Test(description = "GET /users?page=1 returns page=1 with data")
    @Story("Verify page 1 user listing")
    public void testGetUsersPage1() {
        given(requestSpec)
                .queryParam("page", 1)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0))
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test(description = "GET /users?page=2 each user has required fields")
    @Story("Verify required fields on page 2")
    public void testGetUsersPage2() {
        given(requestSpec)
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data.id", everyItem(notNullValue()))
                .body("data.email", everyItem(containsString("@")))
                .body("data.first_name", everyItem(not(emptyString())))
                .body("data.last_name", everyItem(not(emptyString())))
                .body("data.avatar", everyItem(containsString("https")));
    }

    @Test(description = "GET /users/3 returns correct user data")
    @Story("Verify single user details")
    public void testGetSingleUser() {
        given(requestSpec)
                .when()
                .get("/users/3")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id", equalTo(3))
                .body("data.email", containsString("@reqres.in"))
                .body("data.first_name", not(emptyString()));
    }

    @Test(description = "GET /users/9999 returns 404 with empty body")
    @Story("Verify non-existent user handling")
    public void testGetNonExistentUser() {
        given(requestSpec)
                .when()
                .get("/users/9999")
                .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("$", anEmptyMap());
    }
}
