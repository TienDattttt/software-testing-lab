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

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        // Chờ trang cart load xong trước khi dùng
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    /**
     * Lấy số lượng item — luôn re-find tại thời điểm gọi.
     */
    public int getItemCount() {
        return driver.findElements(By.cssSelector(".cart_item")).size();
    }

    /**
     * Xóa item đầu tiên — re-find button tại thời điểm gọi.
     */
    public CartPage removeFirstItem() {
        List<WebElement> buttons = driver.findElements(
                By.cssSelector("[data-test^='remove']"));
        if (!buttons.isEmpty()) {
            WebElement btn = buttons.get(0);
            waitAndClick(btn);
            wait.until(ExpectedConditions.stalenessOf(btn));
        }
        return this;
    }

    /**
     * Điều hướng sang checkout — chờ URL thay đổi.
     * Nếu giỏ rỗng SauceDemo không navigate → trả về false khi check.
     */
    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        return new CheckoutPage(driver);
    }

    /**
     * Lấy tên item — luôn re-find để tránh stale element.
     */
    public List<String> getItemNames() {
        try {
            // Chờ cart_item xuất hiện trước khi lấy tên
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".cart_item")));
            return driver.findElements(
                            By.cssSelector(".cart_item .inventory_item_name"))
                    .stream()
                    .map(el -> el.getText().trim())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("[CartPage] getItemNames() = [] | " + e.getMessage());
            return Collections.emptyList();
        }
    }
}