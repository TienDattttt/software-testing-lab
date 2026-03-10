package com.example.bai1_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class TitleTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // --- Thử Chrome trước ---
        try {
            ChromeOptions options = new ChromeOptions();
            // Thêm các options để tránh lỗi môi trường
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);

        } catch (Exception e) {
            // --- Nếu Chrome lỗi, fallback sang Edge ---
            System.out.println("Chrome khong kha dung, thu Edge...");
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*");
            driver = new EdgeDriver(edgeOptions);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Kiem thu tieu de trang chu")
    public void testTitle() {
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Tieu de trang khong dung!");
    }

    @Test(description = "Kiem thu URL trang chu")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("saucedemo"), "URL khong hop le!");
    }

    @Test(description = "Kiem thu noi dung page source")
    public void testPageSource() {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Swag Labs"),
                "Page source khong chua ten thuong hieu!");
        Assert.assertTrue(pageSource.contains("login-button"),
                "Page source khong chua nut dang nhap!");
        Assert.assertTrue(pageSource.contains("user-name"),
                "Page source khong chua truong username!");
        System.out.println(" Page source hop le, do dai: " + pageSource.length() + " ky tu");
    }

    @Test(description = "Kiem thu form dang nhap co hien thi")
    public void testLoginFormVisible() {
        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton   = driver.findElement(By.id("login-button"));

        Assert.assertTrue(usernameInput.isDisplayed(), "O nhap Username khong hien thi!");
        Assert.assertTrue(passwordInput.isDisplayed(), "O nhap Password khong hien thi!");
        Assert.assertTrue(loginButton.isDisplayed(),   "Nut Login khong hien thi!");

        Assert.assertEquals(usernameInput.getAttribute("placeholder"), "Username",
                "Placeholder Username khong dung!");
        Assert.assertEquals(passwordInput.getAttribute("placeholder"), "Password",
                "Placeholder Password khong dung!");

        System.out.println(" Form dang nhap hien thi day du!");
    }
}