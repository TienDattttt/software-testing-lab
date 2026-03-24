package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    // ==================== LOCATORS ====================
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessageElement;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitForPageLoad();
            wait.until(ExpectedConditions.visibilityOfAllElements(
                    usernameField,
                    passwordField,
                    loginButton
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public InventoryPage login(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }


    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return this;
    }


    public String getErrorMessage() {
        return getText(errorMessageElement);
    }


    public boolean isErrorDisplayed() {
        return isElementVisible(By.cssSelector("[data-test='error']"));
    }

    public boolean isLoginFormVisible() {
        return isLoaded();
    }

    public String getUsernamePlaceholder() {
        return getAttribute(usernameField, "placeholder");
    }

    public String getPasswordPlaceholder() {
        return getAttribute(passwordField, "placeholder");
    }

    public String getLogoText() {
        return getText(loginLogo);
    }
}
