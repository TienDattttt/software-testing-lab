package com.example.seleniumframework.framework.utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "target/screenshots/";


    public static String capture(WebDriver driver, String testName) {

        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }


        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + fileName;

        try {

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = Paths.get(filePath);
            Files.copy(srcFile.toPath(), destination);
            System.out.println("[Screenshot] Đã lưu: " + filePath);
            return filePath;
        } catch (IOException e) {
            System.err.println("[Screenshot] Lỗi khi lưu ảnh: " + e.getMessage());
            return "";
        }
    }


    public static byte[] captureAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
