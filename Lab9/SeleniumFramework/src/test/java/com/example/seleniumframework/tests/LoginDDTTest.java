package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.pages.InventoryPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import com.example.seleniumframework.framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginDDTTest extends BaseTest {

    private static final String EXCEL_PATH =
            "src/test/resources/testdata/login_data.xlsx";


    @DataProvider(name = "smokeCases", parallel = false)
    public Object[][] getSmokeData() {
        return ExcelReader.getData(EXCEL_PATH, "SmokeCases");
    }


    @DataProvider(name = "negativeCases", parallel = false)
    public Object[][] getNegativeData() {
        return ExcelReader.getData(EXCEL_PATH, "NegativeCases");
    }


    @DataProvider(name = "boundaryCases", parallel = false)
    public Object[][] getBoundaryData() {
        return ExcelReader.getData(EXCEL_PATH, "BoundaryCases");
    }


    @Test(
            groups      = {"smoke", "regression"},
            dataProvider = "smokeCases",
            // testName = "{3}" → TestNG dùng tham số index 3 (description) làm tên test
            // Kết quả: "TC_SMOKE_01: Đăng nhập standard_user thành công" thay vì "[0]"
            description = "Smoke: Đăng nhập thành công từ Excel"
    )
    public void testLoginSmoke(String username, String password,
                               String expectedUrl, String description) {
        System.out.println("\n[SMOKE] Đang chạy: " + description);
        System.out.println("  → username: " + username);

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(),
                "[SMOKE FAIL] " + description
                        + " | Trang inventory không hiển thị");

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains(expectedUrl),
                "[SMOKE FAIL] " + description
                        + " | URL không chứa '" + expectedUrl + "'"
                        + " | Actual URL: " + getDriver().getCurrentUrl()
        );
    }


    @Test(
            groups       = {"negative", "regression"},
            dataProvider = "negativeCases",
            description  = "Negative: Đăng nhập thất bại từ Excel"
    )
    public void testLoginNegative(String username, String password,
                                  String expectedError, String description) {
        System.out.println("\n[NEGATIVE] Đang chạy: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        // Kiểm tra error message phải hiển thị
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "[NEGATIVE FAIL] " + description
                        + " | Error message không hiển thị");

        // Kiểm tra nội dung lỗi đúng
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(
                actualError.contains(expectedError),
                "[NEGATIVE FAIL] " + description
                        + "\n  Expected chứa : '" + expectedError + "'"
                        + "\n  Actual        : '" + actualError + "'"
        );
    }


    @Test(
            groups       = {"boundary", "regression"},
            dataProvider = "boundaryCases",
            description  = "Boundary: Dữ liệu biên từ Excel"
    )
    public void testLoginBoundary(String username, String password,
                                  String expectedError, String description) {
        System.out.println("\n[BOUNDARY] Đang chạy: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);


        boolean stayedOnLogin = loginPage.isErrorDisplayed()
                || getDriver().getCurrentUrl().contains("saucedemo.com")
                && !getDriver().getCurrentUrl().contains("inventory");

        Assert.assertTrue(stayedOnLogin,
                "[BOUNDARY FAIL] " + description
                        + " | Trang đã bị redirect — boundary input không được xử lý đúng");

        // Nếu có error message thì kiểm tra nội dung
        if (loginPage.isErrorDisplayed()) {
            String actualError = loginPage.getErrorMessage();
            Assert.assertTrue(
                    actualError.contains(expectedError),
                    "[BOUNDARY FAIL] " + description
                            + "\n  Expected chứa : '" + expectedError + "'"
                            + "\n  Actual        : '" + actualError + "'"
            );
        }
    }
}