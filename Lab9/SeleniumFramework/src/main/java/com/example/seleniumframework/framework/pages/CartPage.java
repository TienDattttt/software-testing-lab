package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CART_ITEM_NAMES = By.cssSelector(".cart_item .inventory_item_name");
    private static final By REMOVE_BUTTONS = By.cssSelector("[data-test^='remove']");
    private static final By CHECKOUT_FORM = By.id("first-name");

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public int getItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public CartPage removeFirstItem() {
        int initialCount = getItemCount();
        List<WebElement> buttons = driver.findElements(REMOVE_BUTTONS);

        if (!buttons.isEmpty()) {
            WebElement button = buttons.get(0);
            waitAndClick(button);
            wait.until(driver -> getItemCount() == Math.max(0, initialCount - 1));
        }

        return this;
    }

    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(CHECKOUT_FORM));
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        return new CheckoutPage(driver);
    }

    public List<String> getItemNames() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(CART_ITEMS));
            return driver.findElements(CART_ITEM_NAMES)
                    .stream()
                    .map(element -> element.getText().trim())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("[CartPage] getItemNames() = [] | " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
