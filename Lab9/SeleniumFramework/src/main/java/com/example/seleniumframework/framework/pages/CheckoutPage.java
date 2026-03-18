package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.id("first-name")));
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

        // Click vào field trước để focus, rồi mới type
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        firstNameField.click();
        waitAndType(firstNameField, firstName);

        lastNameField.click();
        waitAndType(lastNameField, lastName);

        postalCodeField.click();
        waitAndType(postalCodeField, postalCode);

        System.out.println("[CheckoutPage] Giá trị thực tế:");
        System.out.println("  first-name : " + firstNameField.getAttribute("value"));
        System.out.println("  last-name  : " + lastNameField.getAttribute("value"));
        System.out.println("  postal-code: " + postalCodeField.getAttribute("value"));

        waitAndClick(continueButton);
        return this;
    }


    public boolean isOnOverviewPage() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
            return true;
        } catch (Exception e) {
            System.out.println("[CheckoutPage] isOnOverviewPage=false | URL: "
                    + driver.getCurrentUrl());
            return false;
        }
    }
}