package com.example.bai2_testng_groups;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class CheckoutTest {

    WebDriver driver;
    WebDriverWait wait;

    private static final String BASE_URL   = "https://www.saucedemo.com";
    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
        // Login + thêm item + vào cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                .sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASS);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        driver.findElement(By.cssSelector(".inventory_item button")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(groups = {"smoke", "regression"},
            description = "Trang gio hang hien thi san pham")
    public void testCartPageDisplayed() {
        List<WebElement> items = driver.findElements(By.className("cart_item"));
        Assert.assertTrue(items.size() > 0,
                "Cart khong co san pham!");
        System.out.println("[CHECKOUT-smoke] testCartPageDisplayed PASS - " + items.size() + " items");
    }

    @Test(groups = {"regression"},
            description = "Checkout dien thong tin thanh cong")
    public void testCheckoutStep1() {
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("70000");
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Khong chuyen sang step 2!");
        System.out.println("[CHECKOUT-regression] testCheckoutStep1 PASS");
    }


    @Test(groups = {"regression"},
            description = "Checkout bo trong form bao loi")
    public void testCheckoutEmptyForm() {
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        driver.findElement(By.id("continue")).click();
        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']"))
        );
        Assert.assertTrue(error.isDisplayed(),
                "Khong hien loi khi bo trong form!");
        System.out.println("[CHECKOUT-regression] testCheckoutEmptyForm PASS - " + error.getText());
    }
}