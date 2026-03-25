package com.apitesting.tests.lab4;

import com.apitesting.base.ApiBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Lab 10 - API Testing")
@Feature("Lab 4 - Authorization")
public class AuthorizationTest extends ApiBaseTest {

    @Test(description = "Login success returns a valid token")
    @Story("Successful login")
    public void testLoginSuccess() {
        String token = given(requestSpec)
                .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}")
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", not(emptyString()))
                .extract()
                .jsonPath()
                .getString("token");

        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test(description = "Login fails when password is missing")
    @Story("Login missing password")
    public void testLoginMissingPassword() {
        given(requestSpec)
                .body(Map.of("email", "eve.holt@reqres.in"))
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test(description = "Login fails when email is missing")
    @Story("Login missing email")
    public void testLoginMissingEmail() {
        given(requestSpec)
                .body(Map.of("password", "cityslicka"))
                .when()
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }

    @Test(description = "Register success returns id and token")
    @Story("Successful registration")
    public void testRegisterSuccess() {
        given(requestSpec)
                .body(Map.of("email", "eve.holt@reqres.in", "password", "pistol"))
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test(description = "Register fails when password is missing")
    @Story("Register missing password")
    public void testRegisterMissingPassword() {
        given(requestSpec)
                .body(Map.of("email", "sydney@fife"))
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][]{
                {"eve.holt@reqres.in", "cityslicka", 200, null},
                {"eve.holt@reqres.in", "", 400, "Missing password"},
                {"", "cityslicka", 400, "Missing email or username"},
                {"notexist@reqres.in", "wrongpass", 400, "user not found"},
                {"invalid-email", "pass123", 400, "user not found"},
        };
    }

    @Test(dataProvider = "loginScenarios", description = "Data-driven login scenarios")
    @Story("Data-driven login validation")
    public void testLoginScenarios(String email, String password, int expectedStatus, String expectedError) {
        Map<String, String> body = new HashMap<>();
        if (!email.isEmpty()) {
            body.put("email", email);
        }
        if (!password.isEmpty()) {
            body.put("password", password);
        }

        ValidatableResponse response = given(requestSpec)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(expectedStatus);

        if (expectedStatus == 200) {
            response.spec(responseSpec).body("token", not(emptyString()));
        } else {
            response.spec(responseSpec);
        }

        if (expectedError != null) {
            response.body("error", containsString(expectedError));
        }
    }
}
