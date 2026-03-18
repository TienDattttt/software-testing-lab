package com.example.seleniumframework.tests;

import com.example.seleniumframework.framework.base.BaseTest;
import com.example.seleniumframework.framework.pages.InventoryPage;
import com.example.seleniumframework.framework.pages.LoginPage;
import com.example.seleniumframework.framework.utils.JsonReader;
import com.example.seleniumframework.framework.utils.UserData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;


public class UserLoginTest extends BaseTest {

    private static final String JSON_PATH = System.getProperty("user.dir")
            + "/src/test/resources/testdata/users.json";


    @DataProvider(name = "jsonUsers", parallel = false)
    public Object[][] getUsersFromJson() {
        List<UserData> users = JsonReader.readUsers(JSON_PATH);

        // Chuyển List<UserData> → Object[][] cho TestNG
        return users.stream()
                .map(u -> new Object[]{u})
                .toArray(Object[][]::new);
    }


    @Test(
            dataProvider = "jsonUsers",
            description  = "DDT từ JSON: kiểm tra đăng nhập SUCCESS và FAILURE"
    )
    public void testLoginFromJson(UserData user) {
        System.out.println("\n[JSON TEST] " + user.description);
        System.out.println("  → username: " + user.username
                + " | expectSuccess: " + user.expectSuccess);

        LoginPage loginPage = new LoginPage(getDriver());

        if (user.expectSuccess) {
            // Luồng SUCCESS: đăng nhập phải thành công, trang inventory hiển thị
            InventoryPage inventoryPage = loginPage.login(
                    user.username, user.password
            );
            Assert.assertTrue(inventoryPage.isLoaded(),
                    "[FAIL] " + user.description
                            + " | Trang inventory không hiển thị sau khi login");

            System.out.println("   PASS: Đăng nhập thành công, inventory loaded");

        } else {
            // Luồng FAILURE: đăng nhập phải thất bại, error message hiển thị
            loginPage.loginExpectingFailure(user.username, user.password);

            Assert.assertTrue(loginPage.isErrorDisplayed(),
                    "[FAIL] " + user.description
                            + " | Không hiển thị error message khi login thất bại");

            System.out.println("   PASS: Login thất bại đúng như mong đợi, lỗi: "
                    + loginPage.getErrorMessage());
        }
    }
}