package com.example.bai2_testng_groups;


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

    private static final String BASE_URL   = "https://www.saucedemo.com";
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";
    private static final String LOCKED_USER = "locked_out_user";
    private static final String WRONG_PASS  = "wrong_password";

    @BeforeMethod(alwaysRun = true)
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

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void doLogin(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                .sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("login-button")).click();
    }

    private String getError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']"))).getText();
    }

    @Test(groups = {"smoke", "regression"},
            description = "Dang nhap thanh cong")
    public void testLoginSuccess() {
        doLogin(VALID_USER, VALID_PASS);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                " Khong chuyen den inventory!");
        System.out.println(" [LOGIN-smoke] testLoginSuccess PASS");
    }


    @Test(groups = {"regression"},
            description = "Dang nhap sai mat khau")
    public void testLoginWrongPassword() {
        doLogin(VALID_USER, WRONG_PASS);
        Assert.assertTrue(getError().contains("do not match"),
                " Khong hien loi sai mat khau!");
        System.out.println(" [LOGIN-regression] testLoginWrongPassword PASS");
    }


    @Test(groups = {"regression"},
            description = "Tai khoan bi khoa")
    public void testLoginLockedUser() {
        doLogin(LOCKED_USER, VALID_PASS);
        Assert.assertTrue(getError().contains("locked out"),
                " Khong hien loi bi khoa!");
        System.out.println(" [LOGIN-regression] testLoginLockedUser PASS");
    }
}
