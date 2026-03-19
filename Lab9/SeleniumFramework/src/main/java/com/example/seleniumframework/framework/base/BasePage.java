package com.example.seleniumframework.framework.base;

import com.example.seleniumframework.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait())
        );
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait createShortWait() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigReader.getInstance().getShortWait())
        );
    }

    protected void waitAndClick(WebElement element) {
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            clickableElement.click();
        } catch (ElementNotInteractableException
                 | StaleElementReferenceException e) {
            scrollToElement(clickableElement);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", clickableElement);
        }
    }

    protected void waitAndType(WebElement element, String text) {
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));

        try {
            visibleElement.click();
        } catch (WebDriverException ignored) {
            // Some fields are already focused; clicking again is optional.
        }

        visibleElement.clear();
        visibleElement.sendKeys(text);

        try {
            wait.until(driver -> text.equals(element.getAttribute("value")));
        } catch (TimeoutException e) {
            typeWithJs(element, text);
            wait.until(driver -> text.equals(element.getAttribute("value")));
        }
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
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
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
                "const setter = Object.getOwnPropertyDescriptor(" +
                        "window.HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(arguments[0], '');" +
                        "setter.call(arguments[0], arguments[1]);",
                element,
                text);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
    }
}
