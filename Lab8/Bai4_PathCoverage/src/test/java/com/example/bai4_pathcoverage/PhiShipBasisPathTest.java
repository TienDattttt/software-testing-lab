package com.example.bai4_pathcoverage;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PhiShipBasisPathTest {



    @Test(description = "Path 1 [D1=True]: Trong luong = -1 (am) -> throws IllegalArgumentException")
    public void testPath1_TrongLuongKhongHopLe_Am() {

        try {
            PhiShip.tinhPhiShip(-1, "noi_thanh", false);
            Assert.fail("Path 1 FAIL: trong luong am phai throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
        System.out.println("Path 1 PASS [D1=True]: trong luong=-1 throws IllegalArgumentException");
    }

    @Test(description = "Path 1b [D1=True bien]: Trong luong = 0 -> throws IllegalArgumentException")
    public void testPath1b_TrongLuongKhongHopLe_Khong() {
        try {
            PhiShip.tinhPhiShip(0, "ngoai_thanh", true);
            Assert.fail("Path 1b FAIL: trong luong=0 phai throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
        System.out.println("Path 1b PASS [D1=True]: trong luong=0 throws IllegalArgumentException");
    }



    @Test(description = "Path 2 [D2=T,D3=F,D7=F]: noi_thanh, 3kg (<=5), ko member -> phi=15000")
    public void testPath2_NoiThanh_Nhe_KhongMember() {
        double expected = 15000.0;
        double actual = PhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 2 FAIL: noi_thanh 3kg ko member phai la 15000");
        System.out.println("Path 2 PASS [D2=T,D3=F,D7=F]: phi=" + actual);
    }

    @Test(description = "Path 2b [D3=F bien]: noi_thanh, 5kg (bien cua D3=False) -> phi=15000")
    public void testPath2b_NoiThanh_Bien5kg() {

        double expected = 15000.0;
        double actual = PhiShip.tinhPhiShip(5.0, "noi_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 2b FAIL: noi_thanh 5kg (bien D3=F) phai la 15000");
        System.out.println("Path 2b PASS [D3=F bien]: trongLuong=5.0, phi=" + actual);
    }



    @Test(description = "Path 3 [D2=T,D3=T,D7=F]: noi_thanh, 8kg (>5), ko member -> phi=21000")
    public void testPath3_NoiThanh_Nang_KhongMember() {

        double expected = 15000 + (8 - 5) * 2000.0;   // = 21000
        double actual = PhiShip.tinhPhiShip(8, "noi_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 3 FAIL: noi_thanh 8kg ko member phai la 21000");
        System.out.println("Path 3 PASS [D2=T,D3=T,D7=F]: phi=" + actual);
    }


    @Test(description = "Path 4 [D2=T,D3=T,D7=T]: noi_thanh, 8kg (>5), co member -> phi=18900")
    public void testPath4_NoiThanh_Nang_COMember() {
        // phi = 21000 * 0.9 = 18900
        double expected = 21000 * 0.9;                 // = 18900
        double actual = PhiShip.tinhPhiShip(8, "noi_thanh", true);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 4 FAIL: noi_thanh 8kg co member phai la 18900");
        System.out.println("Path 4 PASS [D2=T,D3=T,D7=T]: phi=" + actual + " (giam 10% member)");
    }



    @Test(description = "Path 5 [D4=T,D5=F,D7=F]: ngoai_thanh, 2kg (<=3), ko member -> phi=25000")
    public void testPath5_NgoaiThanh_Nhe_KhongMember() {
        double expected = 25000.0;
        double actual = PhiShip.tinhPhiShip(2, "ngoai_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 5 FAIL: ngoai_thanh 2kg ko member phai la 25000");
        System.out.println("Path 5 PASS [D4=T,D5=F,D7=F]: phi=" + actual);
    }

    @Test(description = "Path 5b [D5=F bien]: ngoai_thanh, 3kg (bien cua D5=False) -> phi=25000")
    public void testPath5b_NgoaiThanh_Bien3kg() {
        // Bien: trongLuong = 3.0 khong > 3, nen D5=False
        double expected = 25000.0;
        double actual = PhiShip.tinhPhiShip(3.0, "ngoai_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 5b FAIL: ngoai_thanh 3kg (bien D5=F) phai la 25000");
        System.out.println("Path 5b PASS [D5=F bien]: trongLuong=3.0, phi=" + actual);
    }



    @Test(description = "Path 6 [D4=T,D5=T,D7=F]: ngoai_thanh, 6kg (>3), ko member -> phi=34000")
    public void testPath6_NgoaiThanh_Nang_KhongMember() {
        // phi = 25000 + (6 - 3) * 3000 = 25000 + 9000 = 34000
        double expected = 25000 + (6 - 3) * 3000.0;   // = 34000
        double actual = PhiShip.tinhPhiShip(6, "ngoai_thanh", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 6 FAIL: ngoai_thanh 6kg ko member phai la 34000");
        System.out.println("Path 6 PASS [D4=T,D5=T,D7=F]: phi=" + actual);
    }



    @Test(description = "Path 7 [D4=F,D6=F,D7=F]: tinh_khac, 1kg (<=2), ko member -> phi=50000")
    public void testPath7_TinhKhac_Nhe_KhongMember() {
        double expected = 50000.0;
        double actual = PhiShip.tinhPhiShip(1, "tinh_khac", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 7 FAIL: tinh_khac 1kg ko member phai la 50000");
        System.out.println("Path 7 PASS [D4=F,D6=F,D7=F]: phi=" + actual);
    }

    @Test(description = "Path 7b [D6=F bien]: tinh_khac, 2kg (bien cua D6=False) -> phi=50000")
    public void testPath7b_TinhKhac_Bien2kg() {

        double expected = 50000.0;
        double actual = PhiShip.tinhPhiShip(2.0, "tinh_khac", false);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 7b FAIL: tinh_khac 2kg (bien D6=F) phai la 50000");
        System.out.println("Path 7b PASS [D6=F bien]: trongLuong=2.0, phi=" + actual);
    }



    @Test(description = "Path 8 [D4=F,D6=T,D7=T]: tinh_khac, 5kg (>2), co member -> phi=58500")
    public void testPath8_TinhKhac_Nang_COMember() {
        // phi = (50000 + (5 - 2) * 5000) * 0.9 = 65000 * 0.9 = 58500
        double expected = (50000 + (5 - 2) * 5000.0) * 0.9;   // = 58500
        double actual = PhiShip.tinhPhiShip(5, "tinh_khac", true);
        Assert.assertEquals(actual, expected, 0.01,
                "Path 8 FAIL: tinh_khac 5kg co member phai la 58500");
        System.out.println("Path 8 PASS [D4=F,D6=T,D7=T]: phi=" + actual + " (giam 10% member)");
    }



    @Test(description = "Extra [D4=T,D5=T,D7=T]: ngoai_thanh, 6kg, co member -> phi=34000*0.9=30600")
    public void testExtra_NgoaiThanh_Nang_COMember() {
        // phi = 34000 * 0.9 = 30600
        double expected = 34000 * 0.9;                 // = 30600
        double actual = PhiShip.tinhPhiShip(6, "ngoai_thanh", true);
        Assert.assertEquals(actual, expected, 0.01,
                "Extra FAIL: ngoai_thanh 6kg co member phai la 30600");
        System.out.println("Extra PASS [D4=T,D5=T,D7=T]: phi=" + actual);
    }

    @Test(description = "Extra [D2=T,D3=F,D7=T]: noi_thanh, 3kg, co member -> phi=15000*0.9=13500")
    public void testExtra_NoiThanh_Nhe_COMember() {
        // phi = 15000 * 0.9 = 13500
        double expected = 15000 * 0.9;                 // = 13500
        double actual = PhiShip.tinhPhiShip(3, "noi_thanh", true);
        Assert.assertEquals(actual, expected, 0.01,
                "Extra FAIL: noi_thanh 3kg co member phai la 13500");
        System.out.println("Extra PASS [D2=T,D3=F,D7=T]: phi=" + actual);
    }
}
