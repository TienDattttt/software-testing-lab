package com.apitesting.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.lessThan;

public class JsonPlaceholderBaseTest {

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass(alwaysRun = true)
    public void setupApiSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(JsonPlaceholderSupport.resolveBaseUri())
                .setBasePath("/")
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
}