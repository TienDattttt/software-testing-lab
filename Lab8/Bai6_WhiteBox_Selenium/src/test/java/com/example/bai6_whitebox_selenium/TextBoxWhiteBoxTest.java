package com.example.bai6_whitebox_selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TextBoxWhiteBoxTest {

    private WebDriver   driver;
    private TextBoxPage textBoxPage;

    private static final String PAGE_URL = "https://demoqa.com/text-box";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");


        driver      = new ChromeDriver(options);
        driver.manage().window().maximize();
        textBoxPage = new TextBoxPage(driver);
        textBoxPage.open();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test(description = "TC1 [N1=T,N2=T,N3=T]: Dien day du thong tin hop le -> output hien thi")
    public void testTC1_FullValidInfo() {
        textBoxPage.fillAndSubmit(
                "Nguyen Van A",
                "nguyenvana@gmail.com",
                "123 Duong Lang, Ha Noi"
        );

        boolean outputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(outputDisplayed,
                "TC1 FAIL: Nhap day du thong tin hop le phai hien thi output section");

        String outputText = textBoxPage.getOutputText();
        Assert.assertTrue(outputText.contains("Nguyen Van A"),
                "TC1 FAIL: Output phai chua ten 'Nguyen Van A'");
        Assert.assertTrue(outputText.contains("nguyenvana@gmail.com"),
                "TC1 FAIL: Output phai chua email");

        System.out.println("TC1 PASS: Output hien thi day du | Output:\n" + outputText);
    }



    @Test(description = "TC2 [N2=F]: Email sai dinh dang 'abc' -> field bi danh dau do")
    public void testTC2_InvalidEmail() {
        textBoxPage.fillAndSubmit(
                "Nguyen Van B",
                "emailsaidinhdan",   // email khong co @
                "456 Duong Nguyen Trai"
        );

        boolean emailInvalid = textBoxPage.isEmailFieldInvalid();
        Assert.assertTrue(emailInvalid,
                "TC2 FAIL: Email sai dinh dang phai danh dau field-error (mau do)");

        System.out.println("TC2 PASS: Email sai dinh dang -> field bi danh dau do (field-error)");
    }



    @Test(description = "TC3 [N1=F,N3=T]: Chi dien dia chi, bo trong ten -> output hien dia chi")
    public void testTC3_OnlyAddress() {
        textBoxPage.fillAndSubmit(
                "",                          // name rong (N1=False)
                "",                          // email rong
                "789 Pho Hue, Hai Ba Trung"  // address co du lieu (N3=True)
        );

        boolean outputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(outputDisplayed,
                "TC3 FAIL: Dien dia chi phai hien thi output");

        String outputText = textBoxPage.getOutputText();
        Assert.assertTrue(outputText.contains("789 Pho Hue"),
                "TC3 FAIL: Output phai chua dia chi");

        System.out.println("TC3 PASS: Chi co address -> output chi hien dia chi:\n" + outputText);
    }


    @Test(description = "TC4 [N1=T,N2=T,N3=F]: Chi ten va email hop le, bo trong address -> output hien name+email")
    public void testTC4_NameAndEmailOnly() {
        textBoxPage.fillAndSubmit(
                "Tran Thi C",
                "tranthic@yahoo.com",
                ""    // address rong (N3=False)
        );

        boolean outputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(outputDisplayed,
                "TC4 FAIL: Nhap ten va email phai hien thi output");

        String outputText = textBoxPage.getOutputText();
        Assert.assertTrue(outputText.contains("Tran Thi C"),
                "TC4 FAIL: Output phai chua ten");

        System.out.println("TC4 PASS: Name + email -> output:\n" + outputText);
    }



    @Test(description = "TC5 [N1=T,N2=F]: Dien ten va email sai dinh dang -> email field do")
    public void testTC5_NamePlusInvalidEmail() {
        textBoxPage.fillAndSubmit(
                "Le Van D",
                "le@van@sai",   // email sai (2 dau @)
                ""
        );

        boolean emailInvalid = textBoxPage.isEmailFieldInvalid();
        Assert.assertTrue(emailInvalid,
                "TC5 FAIL: Email '2 dau @' phai danh dau field-error");

        System.out.println("TC5 PASS: Email '2 dau @' -> field-error (mau do)");
    }


    @Test(description = "TC6 [Boundary]: Ten chua ky tu dac biet va Unicode -> hien binh thuong")
    public void testTC6_Boundary_SpecialCharName() {
        textBoxPage.fillAndSubmit(
                "Nguyễn Văn A (Thạc Sĩ)",  // Unicode + dau ngoac
                "vana@test.com",
                "Q. Hoàn Kiếm, Hà Nội"
        );

        boolean outputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(outputDisplayed,
                "TC6 FAIL: Ten Unicode phai hien thi output binh thuong");

        System.out.println("TC6 PASS: Ten Unicode -> output:\n" + textBoxPage.getOutputText());
    }


    @Test(description = "TC7 [Boundary N1=T, N3=F]: Chi co ten, khong co gi them -> output chi hien ten")
    public void testTC7_Boundary_NameOnly() {
        textBoxPage.fillNameAndSubmit("Pham Thi E");

        boolean outputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(outputDisplayed,
                "TC7 FAIL: Chi nhap ten phai hien thi output");

        String outputText = textBoxPage.getOutputText();
        Assert.assertTrue(outputText.contains("Pham Thi E"),
                "TC7 FAIL: Output phai chua ten 'Pham Thi E'");

        System.out.println("TC7 PASS: Chi ten -> output:\n" + outputText);
    }
}
