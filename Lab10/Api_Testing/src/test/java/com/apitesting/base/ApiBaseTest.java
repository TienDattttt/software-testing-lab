package com.apitesting.base;

import com.apitesting.models.LoginRequest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class ApiBaseTest {

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass(alwaysRun = true)
    public void setupApiSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(3000L))
                .build();
    }

    protected String getAuthToken() {
        return given(requestSpec)
                .body(new LoginRequest("eve.holt@reqres.in", "cityslicka"))
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .spec(responseSpec)
                .extract()
                .jsonPath()
                .getString("token");
    }
}
