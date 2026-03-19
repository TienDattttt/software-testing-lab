package com.example.seleniumframework.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public final class ConfigReader {

    private static ConfigReader instance;

    private final Properties props = new Properties();
    private final String environment;

    private ConfigReader(String environment) {
        this.environment = environment;
        String fileName = "config-" + environment + ".properties";

        try (InputStream inputStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalStateException(
                        "[ConfigReader] Khong tim thay file config: " + fileName
                );
            }

            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(
                    "[ConfigReader] Khong doc duoc file config: " + fileName, e
            );
        }

        System.out.println("[ConfigReader] \u0110ang d\u00f9ng m\u00f4i tr\u01b0\u1eddng: " + environment);
        System.out.println("[ConfigReader] explicit.wait = " + getExplicitWait());
        System.out.println("[ConfigReader] retry.count = " + getRetryCount());
    }

    public static synchronized ConfigReader getInstance() {
        String activeEnvironment = resolveEnvironment();
        if (instance == null || !instance.environment.equals(activeEnvironment)) {
            instance = new ConfigReader(activeEnvironment);
        }
        return instance;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getBaseUrl() {
        return getRequiredProperty("base.url");
    }

    public String getBaseHost() {
        return URI.create(getBaseUrl()).getHost();
    }

    public String getBrowser() {
        return getRequiredProperty("browser");
    }

    public int getExplicitWait() {
        return Integer.parseInt(getRequiredProperty("explicit.wait"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(getRequiredProperty("implicit.wait"));
    }

    public int getShortWait() {
        return Integer.parseInt(getRequiredProperty("short.wait"));
    }

    public int getRetryCount() {
        return Integer.parseInt(getRequiredProperty("retry.count"));
    }

    public String getScreenshotPath() {
        return getRequiredProperty("screenshot.path");
    }

    public String getStandardUsername() {
        return getRequiredProperty("login.standard.username");
    }

    public String getLockedUsername() {
        return getRequiredProperty("login.locked.username");
    }

    public String getDefaultPassword() {
        return getRequiredProperty("login.default.password");
    }

    private String getRequiredProperty(String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "[ConfigReader] Thieu cau hinh bat buoc: " + key
            );
        }
        return value.trim();
    }

    private static String resolveEnvironment() {
        String env = System.getProperty("env", "dev");
        if (env == null || env.isBlank()) {
            return "dev";
        }
        return env.trim().toLowerCase();
    }
}
