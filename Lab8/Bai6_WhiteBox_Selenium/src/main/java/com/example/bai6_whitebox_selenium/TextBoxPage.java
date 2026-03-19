package com.example.bai6_whitebox_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TextBoxPage {

    private final WebDriver       driver;
    private final WebDriverWait   wait;

    @FindBy(id = "userName")
    private WebElement nameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(id = "submit")
    private WebElement submitBtn;

    @FindBy(id = "output")
    private WebElement outputSection;

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /** Mo trang text-box */
    public void open() {
        driver.get("https://demoqa.com/text-box");
        // Cho page load xong
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
    }

    /** Nhap name, email, address roi click submit */
    public void fillAndSubmit(String name, String email, String address) {
        clearAndType(nameField,           name);
        clearAndType(emailField,          email);
        clearAndType(currentAddressField, address);
        clickSubmit();
    }

    /** Chi nhap name roi submit (test TC chi co name) */
    public void fillNameAndSubmit(String name) {
        clearAndType(nameField, name);
        clickSubmit();
    }

    /** Scroll den nut Submit roi click bang JS (tranh bi che boi quang cao) */
    public void clickSubmit() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", submitBtn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", submitBtn);
    }


    public boolean isOutputDisplayed() {
        try {
            WebElement out = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("output")));
            return out.isDisplayed() && !out.getText().isBlank();
        } catch (Exception e) {
            return false;
        }
    }


    public String getOutputText() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("output"))
            ).getText();
        } catch (Exception e) {
            return "";
        }
    }


    public boolean isEmailFieldInvalid() {
        try {
            String cls = emailField.getAttribute("class");
            return cls != null && cls.contains("field-error");
        } catch (Exception e) {
            return false;
        }
    }

    /** Xoa toan bo noi dung field va nhap gia tri moi */
    private void clearAndType(WebElement element, String value) {
        element.clear();
        if (value != null && !value.isEmpty()) {
            element.sendKeys(value);
        }
    }
}
