package com.example.bai6_whitebox_selenium;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class PhoneValidatorTest {



    @Test(description = "Path 1 [N1=True]: phone=null -> false")
    public void testPath1_Null() {
        Assert.assertFalse(PhoneValidator.isValid(null),
                "Path1 FAIL: null phai tra false");
        System.out.println("Path1 PASS: null -> false");
    }

    @Test(description = "Path 1b [N1=True]: phone rong -> false")
    public void testPath1b_Empty() {
        Assert.assertFalse(PhoneValidator.isValid(""),
                "Path1b FAIL: chuoi rong phai tra false");
        System.out.println("Path1b PASS: '' -> false");
    }

    @Test(description = "Path 1c [N1=True]: phone chi chua khoang trang -> false")
    public void testPath1c_BlankSpaces() {
        Assert.assertFalse(PhoneValidator.isValid("   "),
                "Path1c FAIL: khoang trang phai tra false");
        System.out.println("Path1c PASS: '   ' -> false");
    }

    @Test(description = "Path 2 [N2=True]: chua chu cai 'a' -> false")
    public void testPath2_InvalidCharacter_Letter() {
        Assert.assertFalse(PhoneValidator.isValid("09abc12345"),
                "Path2 FAIL: chua chu cai phai tra false");
        System.out.println("Path2 PASS: '09abc12345' -> false [N2=True]");
    }

    @Test(description = "Path 2b [N2=True]: chua ky tu dac biet '@' -> false")
    public void testPath2b_InvalidCharacter_Special() {
        Assert.assertFalse(PhoneValidator.isValid("09@1234567"),
                "Path2b FAIL: chua '@' phai tra false");
        System.out.println("Path2b PASS: '09@1234567' -> false [N2=True]");
    }

    @Test(description = "Path 3 [N3=F, N4=F]: prefix la '1...' (khong phai 0 hay +84) -> false")
    public void testPath3_WrongPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("1234567890"),
                "Path3 FAIL: bat dau bang 1 phai tra false");
        System.out.println("Path3 PASS: '1234567890' -> false [N4=False: prefix sai]");
    }

    @Test(description = "Path 4 [N3=True, N5=False]: +84 nhung chi co 8 chu so sau -> false (sai do dai)")
    public void testPath4_Plus84_WrongLength() {
        // +84 + 8 chu so = sau chuan hoa la 0 + 8 chu so = 9 chu so (sai, can 10)
        Assert.assertFalse(PhoneValidator.isValid("+8412345678"),
                "Path4 FAIL: +84 + 8 chu so phai tra false (do dai 9)");
        System.out.println("Path4 PASS: '+8412345678' -> false [N3=T, N5=F: sai do dai]");
    }

    @Test(description = "Path 5 [N4=True, N5=False]: bat dau bang 0 nhung chi 9 chu so tong -> false")
    public void testPath5_ZeroPrefix_WrongLength() {
        Assert.assertFalse(PhoneValidator.isValid("093456789"),
                "Path5 FAIL: '093456789' (9 chu so) phai tra false");
        System.out.println("Path5 PASS: '093456789' -> false [N4=T, N5=F: do dai 9]");
    }

    @Test(description = "Path 5b [N5=False]: 11 chu so -> false (qua do dai)")
    public void testPath5b_TooLong() {
        Assert.assertFalse(PhoneValidator.isValid("09345678901"),
                "Path5b FAIL: 11 chu so phai tra false");
        System.out.println("Path5b PASS: 11 chu so -> false [N5=F: do dai 11]");
    }

    @Test(description = "Path 6 [N6=False]: bat dau bang '02' (dau so sai '2') -> false")
    public void testPath6_WrongSecondDigit_2() {
        Assert.assertFalse(PhoneValidator.isValid("0212345678"),
                "Path6 FAIL: '021...' (dau so '2' khong hop le) phai tra false");
        System.out.println("Path6 PASS: '0212345678' -> false [N6=F: chu so thu 2 la '2']");
    }

    @Test(description = "Path 6b [N6=False]: bat dau bang '04' (dau so sai '4') -> false")
    public void testPath6b_WrongSecondDigit_4() {
        Assert.assertFalse(PhoneValidator.isValid("0412345678"),
                "Path6b FAIL: '041...' khong hop le");
        System.out.println("Path6b PASS: '0412345678' -> false [N6=F: chu so thu 2 la '4']");
    }

    @Test(description = "Path 7 [N1..N6=True]: '0912345678' -> hop le (dau so 9)")
    public void testPath7_Valid_Prefix09() {
        Assert.assertTrue(PhoneValidator.isValid("0912345678"),
                "Path7 FAIL: '0912345678' phai la hop le");
        System.out.println("Path7 PASS: '0912345678' -> true [N6=T: dau so 9]");
    }


    @Test(description = "Hop le: bat dau bang '03x' -> true")
    public void testValid_Prefix03() {
        Assert.assertTrue(PhoneValidator.isValid("0312345678"),
                "FAIL: '0312345678' phai hop le");
        System.out.println("PASS: dau so 03x -> true");
    }

    @Test(description = "Hop le: bat dau bang '05x' -> true")
    public void testValid_Prefix05() {
        Assert.assertTrue(PhoneValidator.isValid("0512345678"),
                "FAIL: '0512345678' phai hop le");
        System.out.println("PASS: dau so 05x -> true");
    }

    @Test(description = "Hop le: bat dau bang '07x' -> true")
    public void testValid_Prefix07() {
        Assert.assertTrue(PhoneValidator.isValid("0712345678"),
                "FAIL: '0712345678' phai hop le");
        System.out.println("PASS: dau so 07x -> true");
    }

    @Test(description = "Hop le: bat dau bang '08x' -> true")
    public void testValid_Prefix08() {
        Assert.assertTrue(PhoneValidator.isValid("0812345678"),
                "FAIL: '0812345678' phai hop le");
        System.out.println("PASS: dau so 08x -> true");
    }

    @Test(description = "Hop le: dinh dang +84 chuan hoa -> true")
    public void testValid_Plus84Format() {
        // +84912345678 -> chuan hoa -> 0912345678
        Assert.assertTrue(PhoneValidator.isValid("+84912345678"),
                "FAIL: '+84912345678' phai hop le sau chuan hoa");
        System.out.println("PASS: '+84912345678' -> chuan hoa -> '0912345678' -> true");
    }

    @Test(description = "Hop le: so dien thoai co dau cach (space) -> true")
    public void testValid_WithSpaces() {
        // Chap nhan space: '0912 345 678' -> loai space -> '0912345678'
        Assert.assertTrue(PhoneValidator.isValid("0912 345 678"),
                "FAIL: so co space phai hop le sau khi loai space");
        System.out.println("PASS: '0912 345 678' -> loai space -> true");
    }

    @Test(description = "Hop le: +84 voi space -> true")
    public void testValid_Plus84WithSpace() {
        Assert.assertTrue(PhoneValidator.isValid("+84 912 345 678"),
                "FAIL: '+84 912 345 678' phai hop le");
        System.out.println("PASS: '+84 912 345 678' -> chuan hoa -> true");
    }


    @DataProvider(name = "valid_phones")
    public Object[][] validPhones() {
        return new Object[][] {
            { "0312345678",     "03x format"          },
            { "0512345678",     "05x format"          },
            { "0712345678",     "07x format"          },
            { "0812345678",     "08x format"          },
            { "0912345678",     "09x format"          },
            { "+84912345678",   "+84 format"          },
            { "0912 345 678",   "co khoang trang"     },
            { "+84 912 345 678","chuoi +84 co space"  },
        };
    }

    @Test(dataProvider = "valid_phones",
          description  = "DataProvider: Cac so hop le -> phai tra true")
    public void testDataProvider_ValidPhones(String phone, String moTa) {
        Assert.assertTrue(PhoneValidator.isValid(phone),
                "FAIL [" + moTa + "]: '" + phone + "' phai hop le");
        System.out.println("DataProvider PASS [" + moTa + "]: '" + phone + "' -> true");
    }

    @DataProvider(name = "invalid_phones")
    public Object[][] invalidPhones() {
        return new Object[][] {
            { null,              "null"                     },
            { "",                "rong"                     },
            { "   ",             "khoang trang"             },
            { "09abc12345",      "chua chu cai"             },
            { "1234567890",      "bat dau bang 1"           },
            { "0212345678",      "dau so 2 (sai)"           },
            { "0412345678",      "dau so 4 (sai)"           },
            { "093456789",       "9 chu so (thieu 1)"       },
            { "09345678901",     "11 chu so (du 1)"         },
            { "+8412345678",     "+84 + 8 chu so (thieu 1)" },
        };
    }

    @Test(dataProvider = "invalid_phones",
          description  = "DataProvider: Cac so khong hop le -> phai tra false")
    public void testDataProvider_InvalidPhones(String phone, String moTa) {
        Assert.assertFalse(PhoneValidator.isValid(phone),
                "FAIL [" + moTa + "]: '" + phone + "' phai khong hop le");
        System.out.println("DataProvider PASS [" + moTa + "]: '"
                + phone + "' -> false");
    }
}
