package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.pages.CartPage;
import com.example.seleniumframework.framework.pages.CheckoutPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CheckoutTest extends BaseTest {

    @DataProvider(name = "checkoutInfo", parallel = false)
    public Object[][] getCheckoutInfo() {
        return new Object[][]{
                {"Test", "User", "70000", "Legacy static checkout data"}
        };
    }

    private CartPage loginAndGoToCart() {
        ConfigReader config = getConfig();
        return new LoginPage(getDriver())
                .login(config.getStandardUsername(), config.getDefaultPassword())
                .addFirstItemToCart()
                .goToCart();
    }

    @Test(description = "TC_LEGACY_CHECKOUT_01 - Trang gio hang hien thi san pham")
    public void testCartPageDisplayed() {
        CartPage cartPage = loginAndGoToCart();

        Assert.assertTrue(cartPage.getItemCount() > 0,
                "TC_LEGACY_CHECKOUT_01 FAIL: Cart khong co san pham");
    }

    @Test(
            dataProvider = "checkoutInfo",
            description = "TC_LEGACY_CHECKOUT_02 - Checkout dien thong tin thanh cong"
    )
    public void testCheckoutStep1(String firstName, String lastName,
                                  String postalCode, String description) {
        CheckoutPage checkoutPage = loginAndGoToCart().goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "FAIL [" + description + "]: Trang checkout-step-one chua load");

        checkoutPage.fillInfo(firstName, lastName, postalCode);

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "FAIL [" + description + "]: Khong chuyen sang checkout-step-two");
    }

    @Test(description = "TC_LEGACY_CHECKOUT_03 - Checkout bo trong form bao loi")
    public void testCheckoutEmptyForm() {
        CheckoutPage checkoutPage = loginAndGoToCart().goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "TC_LEGACY_CHECKOUT_03 FAIL: Trang checkout-step-one chua load");

        checkoutPage.continueWithoutInfo();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "TC_LEGACY_CHECKOUT_03 FAIL: Khong hien thi loi khi de trong form");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"),
                "TC_LEGACY_CHECKOUT_03 FAIL: Noi dung loi khong dung. Actual: "
                        + checkoutPage.getErrorMessage());
    }
}
