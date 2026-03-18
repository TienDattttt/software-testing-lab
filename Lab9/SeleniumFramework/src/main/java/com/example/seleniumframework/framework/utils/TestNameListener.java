package com.example.seleniumframework.framework.utils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class TestNameListener extends TestListenerAdapter {


    @Override
    public void onTestStart(ITestResult result) {
        Object[] params = result.getParameters();


        if (params != null && params.length > 0) {
            String description = params[params.length - 1].toString();


            result.setTestName(description);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(" PASS: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(" FAIL: " + result.getName());
        System.out.println("   Lỗi: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(" SKIP: " + result.getName());
    }
}