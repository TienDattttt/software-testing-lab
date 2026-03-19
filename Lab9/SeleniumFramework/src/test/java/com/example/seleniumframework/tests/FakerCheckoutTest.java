package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.pages.CartPage;
import com.example.seleniumframework.framework.pages.CheckoutPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import com.example.seleniumframework.framework.utils.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Test(singleThreaded = true)
public class FakerCheckoutTest extends BaseTest {

    private CartPage loginAndAddToCart() {
        ConfigReader config = getConfig();
        return new LoginPage(getDriver())
                .login(config.getStandardUsername(), config.getDefaultPassword())
                .addFirstItemToCart()
                .goToCart();
    }

    @Test(description = "TC_FAKER_01: Checkout voi Faker data - lan 1")
    public void testCheckoutWithFakerData() {
        Map<String, String> data = TestDataFactory.randomCheckoutData();
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        String postalCode = data.get("postalCode");

        System.out.println("\n[FAKER_01] Data sinh ra:");
        System.out.println("  firstName  = " + firstName);
        System.out.println("  lastName   = " + lastName);
        System.out.println("  postalCode = " + postalCode);

        CheckoutPage checkoutPage = loginAndAddToCart().goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "[FAKER_01] Trang checkout-step-one chua load | URL: "
                        + getDriver().getCurrentUrl());

        checkoutPage.fillInfo(firstName, lastName, postalCode);

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "[FAKER_01] Khong chuyen sang checkout-step-two | URL: "
                        + getDriver().getCurrentUrl()
                        + " | Data: " + firstName + "/" + lastName + "/" + postalCode);

        System.out.println("   PASS: Checkout thanh cong");
    }

    @Test(description = "TC_FAKER_02: Checkout voi Faker data - lan 2")
    public void testCheckoutWithFakerDataSecondRun() {
        Map<String, String> data = TestDataFactory.randomCheckoutData();
        String firstName = data.get("firstName");
        String lastName = data.get("lastName");
        String postalCode = data.get("postalCode");

        System.out.println("\n[FAKER_02] Data sinh ra:");
        System.out.println("  firstName  = " + firstName);
        System.out.println("  lastName   = " + lastName);
        System.out.println("  postalCode = " + postalCode);

        CheckoutPage checkoutPage = loginAndAddToCart().goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "[FAKER_02] Trang checkout-step-one chua load | URL: "
                        + getDriver().getCurrentUrl());

        checkoutPage.fillInfo(firstName, lastName, postalCode);

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "[FAKER_02] Khong chuyen sang checkout-step-two | URL: "
                        + getDriver().getCurrentUrl());

        System.out.println("   PASS: Checkout thanh cong");
    }

    @Test(description = "TC_FAKER_03: Xac nhan Faker sinh data khac nhau")
    public void testFakerGeneratesDifferentData() {
        System.out.println("\n[FAKER_03] Sinh 5 firstName lien tiep:");

        String[] names = new String[5];
        for (int i = 0; i < 5; i++) {
            names[i] = TestDataFactory.randomFirstName();
            System.out.println("  Lan " + (i + 1) + ": " + names[i]);
        }

        boolean hasUniqueValues = false;
        for (int i = 1; i < names.length; i++) {
            if (!names[i].equals(names[0])) {
                hasUniqueValues = true;
                break;
            }
        }

        Assert.assertTrue(hasUniqueValues,
                "Faker khong random - tat ca 5 gia tri deu giong nhau!");
        System.out.println("   PASS: Faker sinh data da dang");
    }
}
