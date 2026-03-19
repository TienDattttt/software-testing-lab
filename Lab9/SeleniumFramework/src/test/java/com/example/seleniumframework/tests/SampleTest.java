package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test(description = "Kiem tra trang hien tai thuoc moi truong dang chay")
    public void testPageLoadsSuccessfully() {
        String currentUrl = getDriver().getCurrentUrl();
        String expectedHost = getConfig().getBaseHost();
        System.out.println("[Test] URL hien tai: " + currentUrl);
        Assert.assertTrue(
                currentUrl.contains(expectedHost),
                "URL khong thuoc moi truong hien tai: " + currentUrl
        );
    }

    @Test(description = "Test co tinh FAIL de kiem tra screenshot")
    public void testIntentionalFail() {
        String title = getDriver().getTitle();
        System.out.println("[Test] Title trang: " + title);

        Assert.assertEquals(title, "WRONG_TITLE_TO_TRIGGER_SCREENSHOT",
                "Assertion sai co chu dich - kiem tra file PNG trong target/screenshots/");
    }

    @Test(description = "Kiem tra title cua trang")
    public void testPageTitle() {
        String title = getDriver().getTitle();
        System.out.println("[Test] Title: " + title + " | Thread: "
                + Thread.currentThread().getId());
        Assert.assertFalse(title.isEmpty(), "Title trang khong duoc rong");
    }
}
