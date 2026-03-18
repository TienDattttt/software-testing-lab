package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SampleTest extends BaseTest {


    @Test(description = "Kiểm tra trang SauceDemo load thành công")
    public void testPageLoadsSuccessfully() {
        String currentUrl = getDriver().getCurrentUrl();
        System.out.println("[Test] URL hiện tại: " + currentUrl);
        Assert.assertTrue(
                currentUrl.contains("saucedemo"),
                "URL không chứa 'saucedemo' — trang chưa load đúng"
        );
    }


    @Test(description = "Test cố tình FAIL để kiểm tra screenshot")
    public void testIntentionalFail() {
        String title = getDriver().getTitle();
        System.out.println("[Test] Title trang: " + title);

        // Assertion sai có chủ đích → trigger @AfterMethod chụp ảnh
        Assert.assertEquals(title, "WRONG_TITLE_TO_TRIGGER_SCREENSHOT",
                "Assertion sai có chủ đích — kiểm tra file PNG trong target/screenshots/");
    }


    @Test(description = "Kiểm tra title của trang")
    public void testPageTitle() {
        String title = getDriver().getTitle();
        System.out.println("[Test] Title: " + title + " | Thread: "
                + Thread.currentThread().getId());
        Assert.assertFalse(title.isEmpty(), "Title trang không được rỗng");
    }
}