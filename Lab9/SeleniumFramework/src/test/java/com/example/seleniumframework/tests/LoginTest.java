package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
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
        // SỬA "LoginCases" → "NegativeCases"
        Object[][] allData = ExcelReader.getData(path, "NegativeCases");
        return java.util.Arrays.stream(allData)
                .filter(row -> !row[2].toString().equals("SUCCESS"))
                .toArray(Object[][]::new);
    }


    @Test(description = "TC01 - Đăng nhập thành công")
    public void testLoginSuccess() {
        // Dữ liệu lấy từ config — không hardcode trong test
        String username = "standard_user";
        String password = "secret_sauce";

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(),
                "TC01 FAIL: Trang inventory chưa hiển thị sau khi đăng nhập");
    }


    @Test(description = "TC02 - Đăng nhập với user bị khóa")
    public void testLoginLockedUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "TC02 FAIL: Không hiển thị thông báo lỗi");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("locked out"),
                "TC02 FAIL: Nội dung lỗi không đúng. Actual: " + loginPage.getErrorMessage()
        );
    }


    @Test(description = "TC03 - Username rỗng")
    public void testLoginEmptyUsername() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "TC03 FAIL: Error message không hiển thị");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username is required"),
                "TC03 FAIL: Nội dung lỗi sai. Actual: " + loginPage.getErrorMessage()
        );
    }


    @Test(description = "TC04 - DDT: Đăng nhập thất bại từ Excel",
            dataProvider = "invalidLoginData")
    public void testLoginFailureDDT(String username, String password,
                                    String expectedMsg, String description) {
        System.out.println("[LoginTest] Đang test: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "FAIL [" + description + "]: Error message không hiển thị");

        // Kiểm tra nội dung lỗi chứa chuỗi mong đợi
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedMsg),
                "FAIL [" + description + "]: Expected chứa '" + expectedMsg
                        + "' nhưng Actual là '" + actualError + "'");
    }
}