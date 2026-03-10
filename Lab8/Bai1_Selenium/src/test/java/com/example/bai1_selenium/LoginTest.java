package com.example.bai1_selenium;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    private static final String BASE_URL      = "https://www.saucedemo.com";
    private static final String VALID_USER     = "standard_user";
    private static final String VALID_PASS     = "secret_sauce";
    private static final String LOCKED_USER    = "locked_out_user";
    private static final String WRONG_PASS     = "wrong_password_123";


    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    private void doLogin(String username, String password) {
        // Explicit Wait: chờ field username xuất hiện rồi mới nhập
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))
        );
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton   = driver.findElement(By.id("login-button"));

        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        loginButton.click();
    }

    /**
     * Lấy text thông báo lỗi sau khi login thất bại
     */
    private String getErrorMessage() {
        // Explicit Wait: chờ error message xuất hiện
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='error']")
                )
        );
        return errorMsg.getText();
    }

    @Test(description = "Dang nhap thanh cong voi tai khoan hop le")
    public void testLoginSuccess() {
        doLogin(VALID_USER, VALID_PASS);

        // Explicit Wait: chờ URL chuyển sang /inventory.html
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("inventory.html"),
                "Dang nhap thanh cong nhung KHONG chuyen den /inventory.html! URL hien tai: " + currentUrl
        );

        System.out.println("TC1 PASS: Dang nhap thanh cong, URL = " + currentUrl);
    }

    @Test(description = "Dang nhap voi mat khau sai")
    public void testLoginWrongPassword() {
        doLogin(VALID_USER, WRONG_PASS);

        String errorText = getErrorMessage();

        Assert.assertTrue(
                errorText.contains("Username and password do not match"),
                "Nhap sai pass nhung khong hien thong bao loi dung! Actual: " + errorText
        );

        System.out.println("TC2 PASS: Hien thi loi dung - " + errorText);
    }


    @Test(description = "Dang nhap khi bo trong username")
    public void testLoginEmptyUsername() {
        doLogin("", VALID_PASS);  // username rỗng

        String errorText = getErrorMessage();

        Assert.assertTrue(
                errorText.contains("Username is required"),
                "Bo trong username nhung khong hien thong bao 'Username is required'! Actual: " + errorText
        );

        System.out.println("TC3 PASS: Hien thi loi dung - " + errorText);
    }

    @Test(description = "Dang nhap khi bo trong password")
    public void testLoginEmptyPassword() {
        doLogin(VALID_USER, "");  // password rỗng

        String errorText = getErrorMessage();

        Assert.assertTrue(
                errorText.contains("Password is required"),
                "Bo trong password nhung khong hien thong bao 'Password is required'! Actual: " + errorText
        );

        System.out.println("TC4 PASS: Hien thi loi dung - " + errorText);
    }

    @Test(description = "Dang nhap voi tai khoan bi khoa")
    public void testLoginLockedUser() {
        doLogin(LOCKED_USER, VALID_PASS);

        String errorText = getErrorMessage();

        Assert.assertTrue(
                errorText.contains("Sorry, this user has been locked out"),
                "Dung locked_out_user nhung khong hien thong bao bi khoa! Actual: " + errorText
        );

        System.out.println("TC5 PASS: Hien thi loi dung - " + errorText);
    }
}
