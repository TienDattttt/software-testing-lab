package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.pages.CartPage;
import com.example.seleniumframework.framework.pages.CheckoutPage;
import com.example.seleniumframework.framework.pages.InventoryPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CartTest extends BaseTest {

    private InventoryPage loginAsStandardUser() {
        ConfigReader config = getConfig();
        return new LoginPage(getDriver())
                .login(config.getStandardUsername(), config.getDefaultPassword());
    }

    @Test(description = "TC_LEGACY_CART_01 - Inventory page hien thi danh sach san pham")
    public void testInventoryPageLoaded() {
        InventoryPage inventoryPage = loginAsStandardUser();

        Assert.assertTrue(inventoryPage.isLoaded(),
                "TC_LEGACY_CART_01 FAIL: Trang inventory chua load");
        Assert.assertTrue(inventoryPage.getInventoryItemCount() > 0,
                "TC_LEGACY_CART_01 FAIL: Khong co san pham nao hien thi");
    }

    @Test(description = "TC_LEGACY_CART_02 - Gio hang trong ban dau")
    public void testCartEmptyInitially() {
        InventoryPage inventoryPage = loginAsStandardUser();

        Assert.assertTrue(inventoryPage.isCartEmpty(),
                "TC_LEGACY_CART_02 FAIL: Gio hang ban dau phai trong");
    }

    @Test(description = "TC05 - Them san pham vao gio")
    public void testAddItemToCart() {
        InventoryPage inventoryPage = loginAsStandardUser()
                .addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "TC05 FAIL: Badge gio hang phai la 1 sau khi them 1 san pham");
    }

    @Test(description = "TC06 - Vao gio hang, so luong item dung")
    public void testCartItemCount() {
        CartPage cartPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1,
                "TC06 FAIL: Gio hang phai co dung 1 item");
    }

    @Test(description = "TC07 - Ten san pham trong gio khop")
    public void testCartItemName() {
        String targetProduct = "Sauce Labs Backpack";

        CartPage cartPage = loginAsStandardUser()
                .addItemByName(targetProduct)
                .goToCart();

        Assert.assertTrue(
                cartPage.getItemNames().contains(targetProduct),
                "TC07 FAIL: '" + targetProduct + "' khong co trong gio hang. Actual: "
                        + cartPage.getItemNames()
        );
    }

    @Test(description = "TC08 - Xoa item, gio hang trong")
    public void testRemoveItemFromCart() {
        CartPage cartPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0,
                "TC08 FAIL: Sau khi xoa, gio hang phai rong");
    }

    @Test(description = "TC09 - Vao trang checkout tu gio hang")
    public void testGoToCheckout() {
        CheckoutPage checkoutPage = loginAsStandardUser()
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "TC09 FAIL: Trang checkout chua hien thi");
    }
}
