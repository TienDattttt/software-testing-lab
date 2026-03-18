package com.example.seleniumframework.framework.base;


import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;


public abstract class BaseTest {


    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();


    protected WebDriver getDriver() {
        return tlDriver.get();
    }


    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("dev") String env) {


        System.setProperty("env", env);

        WebDriver driver = createDriver(browser);
        driver.manage().window().maximize();


        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(
                        ConfigReader.getInstance().getImplicitWait()
                ));


        driver.get(ConfigReader.getInstance().getBaseUrl());


        tlDriver.set(driver);

        System.out.println("[BaseTest] Setup xong | Browser: " + browser
                + " | Env: " + env
                + " | Thread: " + Thread.currentThread().getId());
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();

        if (driver != null) {

            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ScreenshotUtil.capture(driver, result.getName());
                System.out.println("[BaseTest] Test FAILED - Screenshot: " + screenshotPath);
            }

            driver.quit();


            tlDriver.remove();
        }

        System.out.println("[BaseTest] Teardown xong | Test: " + result.getName()
                + " | Status: " + getStatusName(result.getStatus()));
    }


    private WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase().trim()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                // Tắt thông báo "Chrome is being controlled by automated software"
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                yield new ChromeDriver(options);
            }
            default -> throw new IllegalArgumentException(
                    "[BaseTest] Browser không được hỗ trợ: " + browser
                            + ". Dùng 'chrome' hoặc 'firefox'."
            );
        };
    }


    private String getStatusName(int status) {
        return switch (status) {
            case ITestResult.SUCCESS -> "PASS ";
            case ITestResult.FAILURE -> "FAIL ";
            case ITestResult.SKIP    -> "SKIP ";
            default -> "UNKNOWN";
        };
    }
}
