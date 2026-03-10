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

public class CartTest {

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
        // Login trước
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                .sendKeys(VALID_USER);
        driver.findElement(By.id("password")).sendKeys(VALID_PASS);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(groups = {"smoke", "regression"},
            description = "Trang san pham hien thi dung")
    public void testInventoryPageLoaded() {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        Assert.assertTrue(items.size() > 0,
                "Khong co san pham nao!");
        System.out.println(" [CART-smoke] testInventoryPageLoaded PASS - " + items.size() + " items");
    }

    @Test(groups = {"regression"},
            description = "Them san pham vao gio hang")
    public void testAddItemToCart() {
        driver.findElement(By.cssSelector(".inventory_item button")).click();
        WebElement badge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))
        );
        Assert.assertEquals(badge.getText(), "1",
                "Badge gio hang khong hien so 1!");
        System.out.println("[CART-regression] testAddItemToCart PASS");
    }

    @Test(groups = {"regression"},
            description = "Gio hang trong ban dau")
    public void testCartEmptyInitially() {
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.size(), 0,
                "Gio hang phai trong!");
        System.out.println("[CART-regression] testCartEmptyInitially PASS");
    }
}