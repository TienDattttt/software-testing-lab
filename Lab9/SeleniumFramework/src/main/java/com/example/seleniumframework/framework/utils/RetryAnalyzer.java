package com.example.seleniumframework.framework.utils;

import com.example.seleniumframework.framework.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int currentRetry = 0;

    @Override
    public boolean retry(ITestResult result) {
        int maxRetry = ConfigReader.getInstance().getRetryCount();

        if (currentRetry < maxRetry) {
            currentRetry++;
            System.out.println("[RetryAnalyzer] RETRY " + currentRetry + "/" + maxRetry
                    + " | Test: " + result.getName());
            return true;
        }

        System.out.println("[RetryAnalyzer] Khong retry nua | Test: " + result.getName()
                + " | retry.count = " + maxRetry);
        return false;
    }
}
