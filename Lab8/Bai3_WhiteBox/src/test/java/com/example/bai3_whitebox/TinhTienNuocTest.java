package com.example.bai3_whitebox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TinhTienNuocTest {


    @Test(description = "TC1 [N1-True]: soM3=0 -> tra ve 0 (khong tinh tien)")
    public void testTC1_SoM3_Khong() {
        double ketQua = TinhTienNuoc.tinhTienNuoc(0, "dan_cu");
        Assert.assertEquals(ketQua, 0.0,
                "TC1 FAIL: soM3=0 phai tra ve 0.0");
        System.out.println("TC1 PASS [N1-True]: soM3=0, loai=dan_cu -> " + ketQua);
    }

    @Test(description = "TC1b [N1-True vien am]: soM3=-5 -> tra ve 0")
    public void testTC1b_SoM3_Am() {
        double ketQua = TinhTienNuoc.tinhTienNuoc(-5, "ho_ngheo");
        Assert.assertEquals(ketQua, 0.0,
                "TC1b FAIL: soM3 am phai tra ve 0.0");
        System.out.println("TC1b PASS [N1-True]: soM3=-5 -> " + ketQua);
    }


    @Test(description = "TC2 [N1-F, N2-True]: loai=ho_ngheo, soM3=5 -> 5*5000=25000")
    public void testTC2_HoNgheo() {
        double expected = 5 * 5000.0;                         // 25000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(5, "ho_ngheo");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC2 FAIL: ho_ngheo 5m3 phai la 25000");
        System.out.println("TC2 PASS [N2-True]: soM3=5, loai=ho_ngheo -> " + ketQua);
    }


    @Test(description = "TC3 [N1-F, N2-F, N3-True, N4-True]: loai=dan_cu, soM3=8 -> 8*7500=60000")
    public void testTC3_DanCu_DuoiOrBang10() {
        double expected = 8 * 7500.0;                         // 60000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(8, "dan_cu");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC3 FAIL: dan_cu 8m3 (<=10) phai la 60000");
        System.out.println("TC3 PASS [N3-T, N4-T]: soM3=8, loai=dan_cu -> " + ketQua);
    }

    @Test(description = "TC3b [N4-True bien]: loai=dan_cu, soM3=10 -> 10*7500=75000 (bien cua N4=True)")
    public void testTC3b_DanCu_Bien10() {
        double expected = 10 * 7500.0;                        // 75000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(10, "dan_cu");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC3b FAIL: dan_cu 10m3 phai la 75000");
        System.out.println("TC3b PASS [N4-T bien]: soM3=10, loai=dan_cu -> " + ketQua);
    }

    @Test(description = "TC4 [N3-T, N4-F, N5-True]: loai=dan_cu, soM3=15 -> 15*9900=148500")
    public void testTC4_DanCu_Muc2() {
        double expected = 15 * 9900.0;                        // 148500.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(15, "dan_cu");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC4 FAIL: dan_cu 15m3 (11-20) phai la 148500");
        System.out.println("TC4 PASS [N5-T]: soM3=15, loai=dan_cu -> " + ketQua);
    }

    @Test(description = "TC4b [N5-True bien]: loai=dan_cu, soM3=20 -> 20*9900=198000 (bien tren N5=True)")
    public void testTC4b_DanCu_Bien20() {
        double expected = 20 * 9900.0;                        // 198000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(20, "dan_cu");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC4b FAIL: dan_cu 20m3 phai la 198000");
        System.out.println("TC4b PASS [N5-T bien]: soM3=20, loai=dan_cu -> " + ketQua);
    }

    @Test(description = "TC5 [N3-T, N4-F, N5-False]: loai=dan_cu, soM3=25 -> 25*11400=285000")
    public void testTC5_DanCu_Muc3() {
        double expected = 25 * 11400.0;                       // 285000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(25, "dan_cu");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC5 FAIL: dan_cu 25m3 (>20) phai la 285000");
        System.out.println("TC5 PASS [N5-F]: soM3=25, loai=dan_cu -> " + ketQua);
    }


    @Test(description = "TC6 [N2-F, N3-False]: loai=kinh_doanh, soM3=10 -> 10*22000=220000")
    public void testTC6_KinhDoanh() {
        double expected = 10 * 22000.0;                       // 220000.0
        double ketQua = TinhTienNuoc.tinhTienNuoc(10, "kinh_doanh");
        Assert.assertEquals(ketQua, expected, 0.01,
                "TC6 FAIL: kinh_doanh 10m3 phai la 220000");
        System.out.println("TC6 PASS [N3-F]: soM3=10, loai=kinh_doanh -> " + ketQua);
    }



    @Test(description = "Boundary: soM3=1 (bien duoi hop le) voi ho_ngheo -> 1*5000=5000")
    public void testBoundary_SoM3_Mot_HoNgheo() {
        Assert.assertEquals(TinhTienNuoc.tinhTienNuoc(1, "ho_ngheo"), 5000.0, 0.01,
                "Boundary FAIL: 1m3 ho_ngheo phai la 5000");
        System.out.println("Boundary PASS: soM3=1, ho_ngheo -> 5000.0");
    }

    @Test(description = "Boundary: soM3=11 (vua qua nguong N4) voi dan_cu -> 11*9900=108900")
    public void testBoundary_DanCu_11m3() {
        double expected = 11 * 9900.0;                        // 108900.0
        Assert.assertEquals(TinhTienNuoc.tinhTienNuoc(11, "dan_cu"), expected, 0.01,
                "Boundary FAIL: dan_cu 11m3 phai la 108900");
        System.out.println("Boundary PASS: soM3=11, dan_cu -> " + expected);
    }

    @Test(description = "Boundary: soM3=21 (vua qua nguong N5) voi dan_cu -> 21*11400=239400")
    public void testBoundary_DanCu_21m3() {
        double expected = 21 * 11400.0;                       // 239400.0
        Assert.assertEquals(TinhTienNuoc.tinhTienNuoc(21, "dan_cu"), expected, 0.01,
                "Boundary FAIL: dan_cu 21m3 phai la 239400");
        System.out.println("Boundary PASS: soM3=21, dan_cu -> " + expected);
    }
}
