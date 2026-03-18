package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.pages.CartPage;
import com.example.seleniumframework.framework.pages.CheckoutPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import com.example.seleniumframework.framework.utils.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;


public class FakerCheckoutTest extends BaseTest {


    private CartPage loginAndAddToCart() {
        return new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart();
    }

    @Test(description = "TC_FAKER_01: Checkout với Faker data - lần 1")
    public void testCheckoutWithFakerData() {
        Map<String, String> data = TestDataFactory.randomCheckoutData();
        String firstName  = data.get("firstName");
        String lastName   = data.get("lastName");
        String postalCode = data.get("postalCode");

        System.out.println("\n[FAKER_01] Data sinh ra:");
        System.out.println("  firstName  = " + firstName);
        System.out.println("  lastName   = " + lastName);
        System.out.println("  postalCode = " + postalCode);

        CheckoutPage checkoutPage = loginAndAddToCart().goToCheckout();

        // Assert 1: Trang checkout bước 1 phải load được
        Assert.assertTrue(checkoutPage.isLoaded(),
                "[FAKER_01] Trang checkout-step-one chưa load | URL: "
                        + getDriver().getCurrentUrl());

        // Điền form
        checkoutPage.fillInfo(firstName, lastName, postalCode);

        // Assert 2: Phải chuyển sang bước 2
        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "[FAKER_01] Không chuyển sang checkout-step-two | URL: "
                        + getDriver().getCurrentUrl()
                        + " | Data: " + firstName + "/" + lastName + "/" + postalCode);

        System.out.println("   PASS: Checkout thành công");
    }


    @Test(description = "TC_FAKER_02: Checkout với Faker data - lần 2 (data khác lần 1)")
    public void testCheckoutWithFakerDataSecondRun() {
        Map<String, String> data = TestDataFactory.randomCheckoutData();
        String firstName  = data.get("firstName");
        String lastName   = data.get("lastName");
        String postalCode = data.get("postalCode");

        System.out.println("\n[FAKER_02] Data sinh ra:");
        System.out.println("  firstName  = " + firstName);
        System.out.println("  lastName   = " + lastName);
        System.out.println("  postalCode = " + postalCode);

        CheckoutPage checkoutPage = loginAndAddToCart().goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(),
                "[FAKER_02] Trang checkout-step-one chưa load | URL: "
                        + getDriver().getCurrentUrl());

        checkoutPage.fillInfo(firstName, lastName, postalCode);

        Assert.assertTrue(checkoutPage.isOnOverviewPage(),
                "[FAKER_02] Không chuyển sang checkout-step-two | URL: "
                        + getDriver().getCurrentUrl());

        System.out.println("   PASS: Checkout thành công");
    }


    @Test(description = "TC_FAKER_03: Xác nhận Faker sinh data khác nhau mỗi lần")
    public void testFakerGeneratesDifferentData() {
        System.out.println("\n[FAKER_03] Sinh 5 firstName liên tiếp:");

        String[] names = new String[5];
        for (int i = 0; i < 5; i++) {
            names[i] = TestDataFactory.randomFirstName();
            System.out.println("  Lần " + (i + 1) + ": " + names[i]);
        }

        boolean hasUniqueValues = false;
        for (int i = 1; i < names.length; i++) {
            if (!names[i].equals(names[0])) {
                hasUniqueValues = true;
                break;
            }
        }

        Assert.assertTrue(hasUniqueValues,
                "Faker không random — tất cả 5 giá trị đều giống nhau!");
        System.out.println("   PASS: Faker sinh data đa dạng");
    }
}