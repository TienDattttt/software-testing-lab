package com.apitesting.tests.lab6;

import com.apitesting.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Lab 10 - API Testing")
@Feature("Lab 6 - API UI Integration")
public class ApiUiIntegrationTest extends BaseTest {

    private boolean isApiAlive = false;
    private String apiToken = null;
    private RequestSpecification requestSpec;

    @BeforeMethod(alwaysRun = true)
    public void checkApiPrecondition() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        try {
            Response loginResponse = given(requestSpec)
                    .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}")
                    .when()
                    .post("/login");

            if (loginResponse.statusCode() == 200) {
                apiToken = loginResponse.jsonPath().getString("token");
                System.out.println("[API Precondition] Token received: " + apiToken);

                Response usersResponse = given(requestSpec)
                        .when()
                        .get("/users");
                int statusCode = usersResponse.statusCode();
                isApiAlive = statusCode == 200;
                System.out.println("[API Precondition] API alive: " + isApiAlive);
            } else {
                isApiAlive = false;
            }
        } catch (Exception e) {
            System.err.println("[API Precondition] API check failed: " + e.getMessage());
            isApiAlive = false;
        }
    }

    @Test(description = "Part A: UI login after API precondition passes")
    @Story("Login to UI only after API precondition succeeds")
    public void testUiLoginAfterApiPrecondition() {
        if (!isApiAlive || apiToken == null) {
            throw new SkipException("Skipping UI test: API precondition failed");
        }

        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"), "URL should contain 'inventory'");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs", "Page title should be 'Swag Labs'");
        System.out.println("[UI] Login successful, URL: " + getDriver().getCurrentUrl());
    }

    @Test(description = "Part B: Full flow - API health check then UI add 2 products")
    @Story("API health check followed by UI cart flow")
    public void testFullApiUiFlow() {
        if (!isApiAlive) {
            throw new SkipException("Skipping: reqres.in API is not alive");
        }
        System.out.println("[API Check] reqres.in is alive");

        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();

        List<WebElement> addButtons = getDriver().findElements(By.cssSelector("[data-test^='add-to-cart']"));
        Assert.assertTrue(addButtons.size() >= 2, "Need at least 2 products");
        addButtons.get(0).click();
        addButtons.get(1).click();

        WebElement badge = getDriver().findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.getText(), "2", "Cart badge should show 2");
        System.out.println("[UI] Cart badge = " + badge.getText());

        getDriver().findElement(By.className("shopping_cart_link")).click();
        List<WebElement> cartItems = getDriver().findElements(By.className("cart_item"));
        Assert.assertEquals(cartItems.size(), 2, "Cart should have 2 items");
        System.out.println("[UI] Cart has " + cartItems.size() + " items");
    }
}
