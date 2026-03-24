package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final By INVENTORY_ITEM_NAME = By.cssSelector(".inventory_item_name");
    private static final By ITEM_BUTTON = By.cssSelector("button");

    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".inventory_item button")
    private List<WebElement> addToCartButtons;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(By.cssSelector(".inventory_list"));
    }

    public InventoryPage addFirstItemToCart() {
        wait.until(ExpectedConditions.visibilityOf(inventoryList));
        waitAndClick(addToCartButtons.get(0));
        return this;
    }

    public InventoryPage addItemByName(String productName) {
        wait.until(ExpectedConditions.visibilityOf(inventoryList));

        for (WebElement item : inventoryItems) {
            String name = item.findElement(INVENTORY_ITEM_NAME).getText().trim();
            if (name.equals(productName)) {
                WebElement button = item.findElement(ITEM_BUTTON);
                waitAndClick(button);
                return this;
            }
        }

        throw new RuntimeException("[InventoryPage] Khong tim thay san pham: " + productName);
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getInventoryItemCount() {
        wait.until(ExpectedConditions.visibilityOf(inventoryList));
        return inventoryItems.size();
    }

    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        waitAndClick(cartLink);
        wait.until(ExpectedConditions.urlContains("cart"));
        return new CartPage(driver);
    }
}
