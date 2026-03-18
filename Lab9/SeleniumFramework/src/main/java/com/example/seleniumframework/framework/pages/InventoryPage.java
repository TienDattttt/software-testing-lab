package com.example.seleniumframework.framework.pages;

import com.example.seleniumframework.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class InventoryPage extends BasePage {

    // ==================== LOCATORS ====================
    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item button")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;


    public InventoryPage(WebDriver driver) {
        super(driver);
    }




    public boolean isLoaded() {
        return isElementVisible(By.cssSelector(".inventory_list"));
    }


    public InventoryPage addFirstItemToCart() {
        waitAndClick(addToCartButtons.get(0));
        return this;
    }


    public InventoryPage addItemByName(String productName) {
        // Tìm container chứa đúng tên sản phẩm, rồi click nút button bên trong
        List<WebElement> items = driver.findElements(By.cssSelector(".inventory_item"));
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            if (name.equals(productName)) {
                item.findElement(By.cssSelector("button")).click();
                return this;
            }
        }
        throw new RuntimeException("[InventoryPage] Không tìm thấy sản phẩm: " + productName);
    }


    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            // Badge không xuất hiện = giỏ hàng rỗng
            return 0;
        }
    }


    public CartPage goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        return new CartPage(driver);
    }
}