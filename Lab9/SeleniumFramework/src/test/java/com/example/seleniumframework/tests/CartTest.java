package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.pages.CartPage;
import com.example.seleniumframework.framework.pages.CheckoutPage;
import com.example.seleniumframework.framework.pages.InventoryPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CartTest extends BaseTest {


    private InventoryPage loginAsStandardUser() {
        return new LoginPage(getDriver())
                .login("standard_user", "secret_sauce");
    }


    @Test(description = "TC05 - Thêm sản phẩm vào giỏ, badge tăng lên 1")
    public void testAddItemToCart() {
        // Fluent Interface — toàn bộ chuỗi action trên 1 dòng
        InventoryPage inventoryPage = loginAsStandardUser()
                .addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "TC05 FAIL: Badge giỏ hàng phải là 1 sau khi thêm 1 sản phẩm");
    }


    @Test(description = "TC06 - Vào giỏ hàng, số lượng item đúng")
    public void testCartItemCount() {
        CartPage cartPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1,
                "TC06 FAIL: Giỏ hàng phải có đúng 1 item");
    }


    @Test(description = "TC07 - Tên sản phẩm trong giỏ khớp với sản phẩm đã thêm")
    public void testCartItemName() {
        String targetProduct = "Sauce Labs Backpack";

        CartPage cartPage = loginAsStandardUser()
                .addItemByName(targetProduct)
                .goToCart();

        Assert.assertTrue(
                cartPage.getItemNames().contains(targetProduct),
                "TC07 FAIL: '" + targetProduct + "' không có trong giỏ hàng. "
                        + "Actual: " + cartPage.getItemNames()
        );
    }


    @Test(description = "TC08 - Xóa item, giỏ hàng trở thành rỗng")
    public void testRemoveItemFromCart() {
        CartPage cartPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0,
                "TC08 FAIL: Sau khi xóa, giỏ hàng phải rỗng");
    }

    @Test(description = "TC09 - Vào trang Checkout từ giỏ hàng")
    public void testGoToCheckout() {
        CheckoutPage checkoutPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "TC09 FAIL: Trang Checkout chưa hiển thị");
    }
}