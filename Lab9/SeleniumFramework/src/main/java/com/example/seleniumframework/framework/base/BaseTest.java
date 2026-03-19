package com.example.seleniumframework.framework.base;

import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return tlDriver.get();
    }

    protected ConfigReader getConfig() {
        return ConfigReader.getInstance();
    }

    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("dev") String env) {

        String activeEnv = normalizeProperty(System.getProperty("env", env), "dev");
        System.setProperty("env", activeEnv);

        ConfigReader config = ConfigReader.getInstance();
        String activeBrowser = normalizeProperty(
                System.getProperty("browser", browser),
                config.getBrowser()
        );

        WebDriver driver = createDriver(activeBrowser);
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

        driver.get(config.getBaseUrl());
        tlDriver.set(driver);

        System.out.println("[BaseTest] Setup xong | Browser: " + activeBrowser
                + " | Env: " + config.getEnvironment()
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
            case "firefox" -> new FirefoxDriver();
            case "chrome" -> {
                suppressChromeCdpWarnings();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                yield new ChromeDriver(options);
            }
            default -> throw new IllegalArgumentException(
                    "[BaseTest] Browser khong duoc ho tro: " + browser
                            + ". Dung 'chrome' hoac 'firefox'."
            );
        };
    }

    private void suppressChromeCdpWarnings() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
                .setLevel(Level.SEVERE);
        Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver")
                .setLevel(Level.SEVERE);
    }

    private String getStatusName(int status) {
        return switch (status) {
            case ITestResult.SUCCESS -> "PASS ";
            case ITestResult.FAILURE -> "FAIL ";
            case ITestResult.SKIP -> "SKIP ";
            default -> "UNKNOWN";
        };
    }

    private String normalizeProperty(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }
}
