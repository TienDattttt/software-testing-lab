package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LandingPageTest extends BaseTest {

    @Test(description = "TC_LEGACY_TITLE_01 - Tieu de trang login dung")
    public void testTitle() {
        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertTrue(loginPage.isLoaded(),
                "TC_LEGACY_TITLE_01 FAIL: Trang login chua load day du");

        Assert.assertEquals(getDriver().getTitle(), "Swag Labs",
                "TC_LEGACY_TITLE_01 FAIL: Tieu de trang khong dung");
    }

    @Test(description = "TC_LEGACY_TITLE_02 - URL thuoc moi truong dang chay")
    public void testEnvironmentUrlLoaded() {
        String currentUrl = getDriver().getCurrentUrl();
        String expectedHost = getConfig().getBaseHost();

        Assert.assertTrue(
                currentUrl.contains(expectedHost),
                "TC_LEGACY_TITLE_02 FAIL: URL khong thuoc moi truong hien tai: " + currentUrl
        );
    }

    @Test(description = "TC_LEGACY_TITLE_03 - Page source chua marker login")
    public void testPageSourceContainsCoreMarkers() {
        String pageSource = getDriver().getPageSource();

        Assert.assertTrue(pageSource.contains("Swag Labs"),
                "TC_LEGACY_TITLE_03 FAIL: Page source khong chua ten thuong hieu");
        Assert.assertTrue(pageSource.contains("login-button"),
                "TC_LEGACY_TITLE_03 FAIL: Page source khong chua nut dang nhap");
        Assert.assertTrue(pageSource.contains("user-name"),
                "TC_LEGACY_TITLE_03 FAIL: Page source khong chua truong username");
    }

    @Test(description = "TC_LEGACY_TITLE_04 - Form dang nhap hien thi day du")
    public void testLoginFormVisible() {
        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertTrue(loginPage.isLoginFormVisible(),
                "TC_LEGACY_TITLE_04 FAIL: Form dang nhap khong hien thi day du");
        Assert.assertEquals(loginPage.getUsernamePlaceholder(), "Username",
                "TC_LEGACY_TITLE_04 FAIL: Placeholder username khong dung");
        Assert.assertEquals(loginPage.getPasswordPlaceholder(), "Password",
                "TC_LEGACY_TITLE_04 FAIL: Placeholder password khong dung");
        Assert.assertEquals(loginPage.getLogoText(), "Swag Labs",
                "TC_LEGACY_TITLE_04 FAIL: Logo text khong dung");
    }
}
