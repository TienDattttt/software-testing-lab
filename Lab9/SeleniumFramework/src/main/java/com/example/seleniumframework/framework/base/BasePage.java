package com.example.seleniumframework.framework.base;


import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;


    public BasePage(WebDriver driver) {
        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        PageFactory.initElements(driver, this);
    }

    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }


    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }


    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element))
                .getText()
                .trim();
    }


    protected boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {

            return false;
        }
    }


    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }


    protected void waitForPageLoad() {
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }


    protected String getAttribute(WebElement element, String attribute) {
        return wait.until(ExpectedConditions.visibilityOf(element))
                .getAttribute(attribute);
    }
    protected void typeWithJs(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = '';", element);       // clear
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];", element, text); // set value
        // Trigger change event để form nhận giá trị
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                element);
    }
}