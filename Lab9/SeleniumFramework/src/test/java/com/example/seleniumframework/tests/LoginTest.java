package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.config.ConfigReader;
import com.example.seleniumframework.framework.pages.InventoryPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import com.example.seleniumframework.framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        String path = System.getProperty("user.dir")
                + "/src/test/resources/testdata/login_data.xlsx";
        Object[][] allData = ExcelReader.getData(path, "NegativeCases");
        return java.util.Arrays.stream(allData)
                .filter(row -> !row[2].toString().equals("SUCCESS"))
                .toArray(Object[][]::new);
    }

    @Test(description = "TC01 - Login success")
    public void testLoginSuccess() {
        ConfigReader config = getConfig();
        String username = config.getStandardUsername();
        String password = config.getDefaultPassword();

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(),
                "TC01 FAIL: Inventory page chua hien thi sau khi dang nhap");
    }

    @Test(description = "TC02 - Login voi user bi khoa")
    public void testLoginLockedUser() {
        ConfigReader config = getConfig();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(
                config.getLockedUsername(),
                config.getDefaultPassword()
        );

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "TC02 FAIL: Khong hien thi thong bao loi");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("locked out"),
                "TC02 FAIL: Noi dung loi khong dung. Actual: "
                        + loginPage.getErrorMessage()
        );
    }

    @Test(description = "TC03 - Username rong")
    public void testLoginEmptyUsername() {
        ConfigReader config = getConfig();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("", config.getDefaultPassword());

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "TC03 FAIL: Error message khong hien thi");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username is required"),
                "TC03 FAIL: Noi dung loi sai. Actual: " + loginPage.getErrorMessage()
        );
    }

    @Test(
            description = "TC04 - DDT login that bai tu Excel",
            dataProvider = "invalidLoginData"
    )
    public void testLoginFailureDDT(String username, String password,
                                    String expectedMsg, String description) {
        System.out.println("[LoginTest] Dang test: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "FAIL [" + description + "]: Error message khong hien thi");

        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedMsg),
                "FAIL [" + description + "]: Expected chua '" + expectedMsg
                        + "' nhung Actual la '" + actualError + "'");
    }
}
