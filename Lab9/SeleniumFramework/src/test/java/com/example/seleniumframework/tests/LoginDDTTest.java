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
            groups = {"smoke", "regression"},
            dataProvider = "smokeCases",
            description = "Smoke: Dang nhap thanh cong tu Excel"
    )
    public void testLoginSmoke(String username, String password,
                               String expectedUrl, String description) {
        System.out.println("\n[SMOKE] Dang chay: " + description);
        System.out.println("  -> username: " + username);

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(),
                "[SMOKE FAIL] " + description
                        + " | Trang inventory khong hien thi");

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains(expectedUrl),
                "[SMOKE FAIL] " + description
                        + " | URL khong chua '" + expectedUrl + "'"
                        + " | Actual URL: " + getDriver().getCurrentUrl()
        );
    }

    @Test(
            groups = {"negative", "regression"},
            dataProvider = "negativeCases",
            description = "Negative: Dang nhap that bai tu Excel"
    )
    public void testLoginNegative(String username, String password,
                                  String expectedError, String description) {
        System.out.println("\n[NEGATIVE] Dang chay: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "[NEGATIVE FAIL] " + description
                        + " | Error message khong hien thi");

        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(
                actualError.contains(expectedError),
                "[NEGATIVE FAIL] " + description
                        + "\n  Expected chua : '" + expectedError + "'"
                        + "\n  Actual        : '" + actualError + "'"
        );
    }

    @Test(
            groups = {"boundary", "regression"},
            dataProvider = "boundaryCases",
            description = "Boundary: Du lieu bien tu Excel"
    )
    public void testLoginBoundary(String username, String password,
                                  String expectedError, String description) {
        System.out.println("\n[BOUNDARY] Dang chay: " + description);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        String currentUrl = getDriver().getCurrentUrl();
        boolean stayedOnLogin = loginPage.isErrorDisplayed()
                || currentUrl.contains(getConfig().getBaseHost())
                && !currentUrl.contains("inventory");

        Assert.assertTrue(stayedOnLogin,
                "[BOUNDARY FAIL] " + description
                        + " | Trang da bi redirect - boundary input khong duoc xu ly dung");

        if (loginPage.isErrorDisplayed()) {
            String actualError = loginPage.getErrorMessage();
            Assert.assertTrue(
                    actualError.contains(expectedError),
                    "[BOUNDARY FAIL] " + description
                            + "\n  Expected chua : '" + expectedError + "'"
                            + "\n  Actual        : '" + actualError + "'"
            );
        }
    }
}
