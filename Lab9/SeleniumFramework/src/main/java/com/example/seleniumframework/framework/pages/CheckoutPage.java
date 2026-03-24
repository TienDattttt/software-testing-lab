package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessageElement;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            return true;
        } catch (Exception e) {
            System.out.println("[CheckoutPage] isLoaded=false | URL: "
                    + driver.getCurrentUrl());
            return false;
        }
    }

    public CheckoutPage fillInfo(String firstName, String lastName,
                                 String postalCode) {
        System.out.println("[CheckoutPage] fillInfo: "
                + firstName + " / " + lastName + " / " + postalCode);

        fillCheckoutField(firstNameField, firstName);
        fillCheckoutField(lastNameField, lastName);
        fillCheckoutField(postalCodeField, postalCode);

        String actualFirstName = getAttribute(firstNameField, "value");
        String actualLastName = getAttribute(lastNameField, "value");
        String actualPostalCode = getAttribute(postalCodeField, "value");

        System.out.println("[CheckoutPage] Gia tri thuc te:");
        System.out.println("  first-name : " + actualFirstName);
        System.out.println("  last-name  : " + actualLastName);
        System.out.println("  postal-code: " + actualPostalCode);

        waitAndClick(continueButton);

        try {
            createShortWait().until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("checkout-step-two"),
                    ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)
            ));
        } catch (TimeoutException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", continueButton);
        }

        return this;
    }

    public CheckoutPage continueWithoutInfo() {
        waitAndClick(continueButton);
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        return this;
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return getText(errorMessageElement);
    }

    public boolean isOnOverviewPage() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
            return true;
        } catch (Exception e) {
            String errorText = "";
            if (isErrorDisplayed()) {
                errorText = getErrorMessage();
            }

            System.out.println("[CheckoutPage] isOnOverviewPage=false | URL: "
                    + driver.getCurrentUrl()
                    + (errorText.isBlank() ? "" : " | Error: " + errorText));
            return false;
        }
    }

    private void fillCheckoutField(WebElement field, String value) {
        waitAndType(field, value);
        typeWithJs(field, value);
    }
}
