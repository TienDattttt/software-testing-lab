package com.example.bai5_mcdc;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class VayVonMCDCTest {

    // Hang so du lieu test
    private static final int    TUOI_HOP_LE    = 25;               // A=True
    private static final int    TUOI_KHONG_HL  = 20;               // A=False
    private static final double THU_NHAP_HL    = 15_000_000.0;     // B=True
    private static final double THU_NHAP_KHL   = 8_000_000.0;      // B=False
    private static final int    TD_TOT          = 750;              // D=True
    private static final int    TD_THAP         = 600;              // D=False


    @Test(description = "MC/DC Row1 [A=T,B=T,C=T,D=T]: Du dieu kien ca 4 -> DUOC VAY")
    public void testMCDC_Row1_TatCaTrue() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_HOP_LE,      // A=True:  25 >= 22
                THU_NHAP_HL,      // B=True:  15tr >= 10tr
                true,             // C=True:  co tai san bao lanh
                TD_TOT            // D=True:  750 >= 700
        );
        Assert.assertTrue(ketQua,
                "Row1 FAIL: A=T,B=T,C=T,D=T phai tra ve TRUE (du dieu kien vay)");
        System.out.println("MC/DC Row1 PASS [A=T,B=T,C=T,D=T]: ketQua=" + ketQua
                + " | Chung minh A(cap:R1vsR9), B(cap:R1vsR5)");
    }


    @Test(description = "MC/DC Row2 [A=T,B=T,C=T,D=F]: Co tai san, ko tin dung tot -> con DUOC VAY (C doc lap)")
    public void testMCDC_Row2_CoTaiSan_KhongTinDung() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_HOP_LE,      // A=True
                THU_NHAP_HL,      // B=True
                true,             // C=True: co tai san bao lanh
                TD_THAP           // D=False: 600 < 700
        );
        Assert.assertTrue(ketQua,
                "Row2 FAIL: A=T,B=T,C=T,D=F phai tra ve TRUE (co tai san du bu)");
        System.out.println("MC/DC Row2 PASS [A=T,B=T,C=T,D=F]: ketQua=" + ketQua
                + " | Chung minh doc lap C (cap: R2=T vs R4=F)");
    }


    @Test(description = "MC/DC Row3 [A=T,B=T,C=F,D=T]: Khong tai san, tin dung tot -> con DUOC VAY (D doc lap)")
    public void testMCDC_Row3_KhongTaiSan_TinDungTot() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_HOP_LE,      // A=True
                THU_NHAP_HL,      // B=True
                false,            // C=False: khong co tai san bao lanh
                TD_TOT            // D=True: 750 >= 700
        );
        Assert.assertTrue(ketQua,
                "Row3 FAIL: A=T,B=T,C=F,D=T phai tra ve TRUE (tin dung tot du bu)");
        System.out.println("MC/DC Row3 PASS [A=T,B=T,C=F,D=T]: ketQua=" + ketQua
                + " | Chung minh doc lap D (cap: R3=T vs R4=F)");
    }


    @Test(description = "MC/DC Row4 [A=T,B=T,C=F,D=F]: Co ban hop le nhung KHONG co bao dam -> KHONG DUOC VAY")
    public void testMCDC_Row4_KhongBaoDam() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_HOP_LE,      // A=True
                THU_NHAP_HL,      // B=True
                false,            // C=False
                TD_THAP           // D=False: 600 < 700
        );
        Assert.assertFalse(ketQua,
                "Row4 FAIL: A=T,B=T,C=F,D=F phai tra ve FALSE (thieu bao dam)");
        System.out.println("MC/DC Row4 PASS [A=T,B=T,C=F,D=F]: ketQua=" + ketQua
                + " | Chung minh C(cap:R2vsR4) va D(cap:R3vsR4) (dieuKienBaoDam=False)");
    }


    @Test(description = "MC/DC Row5 [A=T,B=F,C=T,D=T]: Thu nhap thap (B doc lap) -> KHONG DUOC VAY")
    public void testMCDC_BDocLap_ThuNhapThap() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_HOP_LE,      // A=True:  25 >= 22
                THU_NHAP_KHL,     // B=False: 8tr < 10tr
                true,             // C=True
                TD_TOT            // D=True
        );
        Assert.assertFalse(ketQua,
                "Row5 FAIL: A=T,B=F,C=T,D=T phai tra ve FALSE (thu nhap khong du)");
        System.out.println("MC/DC Row5 PASS [A=T,B=F,C=T,D=T]: ketQua=" + ketQua
                + " | Chung minh doc lap B (cap: R1=T vs R5=F)");
    }


    @Test(description = "MC/DC Row9 [A=F,B=T,C=T,D=T]: Tuoi thap hon 22 (A doc lap) -> KHONG DUOC VAY")
    public void testMCDC_ADocLap_TuoiThapHon22() {
        boolean ketQua = VayVon.duDieuKienVay(
                TUOI_KHONG_HL,    // A=False: 20 < 22
                THU_NHAP_HL,      // B=True
                true,             // C=True
                TD_TOT            // D=True
        );
        Assert.assertFalse(ketQua,
                "Row9 FAIL: A=F,B=T,C=T,D=T phai tra ve FALSE (chua du tuoi)");
        System.out.println("MC/DC Row9 PASS [A=F,B=T,C=T,D=T]: ketQua=" + ketQua
                + " | Chung minh doc lap A (cap: R1=T vs R9=F)");
    }


    @Test(description = "Condition Coverage TC1: A=T,B=T,C=T,D=T -> TRUE (tat ca dieu kien True)")
    public void testConditionCoverage_TatCaTrue() {
        Assert.assertTrue(
                VayVon.duDieuKienVay(25, 15_000_000, true, 750),
                "CC-TC1 FAIL: tat ca True phai tra TRUE"
        );
        System.out.println("Condition Coverage TC1 PASS: A=T,B=T,C=T,D=T -> TRUE");
    }

    @Test(description = "Condition Coverage TC2: A=F,B=F,C=F,D=F -> FALSE (tat ca dieu kien False)")
    public void testConditionCoverage_TatCaFalse() {
        Assert.assertFalse(
                VayVon.duDieuKienVay(18, 5_000_000, false, 500),
                "CC-TC2 FAIL: tat ca False phai tra FALSE"
        );
        System.out.println("Condition Coverage TC2 PASS: A=F,B=F,C=F,D=F -> FALSE");
    }


    @DataProvider(name = "mcdc_data")
    public Object[][] mcdcDataProvider() {
        return new Object[][] {
            //  tuoi   thuNhap         coTS   TD    expected  mo ta row
            {   25,    15_000_000.0,   true,  750,  true,     "Row1 [A=T,B=T,C=T,D=T] -> TRUE"  },
            {   25,    15_000_000.0,   true,  600,  true,     "Row2 [A=T,B=T,C=T,D=F] -> TRUE"  },
            {   25,    15_000_000.0,   false, 750,  true,     "Row3 [A=T,B=T,C=F,D=T] -> TRUE"  },
            {   25,    15_000_000.0,   false, 600,  false,    "Row4 [A=T,B=T,C=F,D=F] -> FALSE" },
            {   25,     8_000_000.0,   true,  750,  false,    "Row5 [A=T,B=F,C=T,D=T] -> FALSE" },
            {   20,    15_000_000.0,   true,  750,  false,    "Row9 [A=F,B=T,C=T,D=T] -> FALSE" },
        };
    }


    @Test(
        dataProvider = "mcdc_data",
        description  = "MC/DC DataProvider: chay ca 6 rows toi thieu bang data-driven"
    )
    public void testMCDC_DataDriven(int tuoi, double thuNhap, boolean coTaiSan,
                                     int dienTinDung, boolean expected, String moTa) {
        boolean actual = VayVon.duDieuKienVay(tuoi, thuNhap, coTaiSan, dienTinDung);
        Assert.assertEquals(actual, expected,
                "DataProvider FAIL cho [" + moTa
                + "]: expected=" + expected + " actual=" + actual);
        System.out.println("DataProvider PASS: " + moTa + " -> actual=" + actual);
    }


    @Test(description = "Boundary A: tuoi=22 (bien duoi du dieu kien) -> DUOC VAY (neu B,C/D hop le)")
    public void testBoundary_A_Tuoi22() {
        // Bien duoi cua A=True: tuoi chinh xac la 22
        Assert.assertTrue(
                VayVon.duDieuKienVay(22, 15_000_000, true, 750),
                "Boundary-A FAIL: tuoi=22 (vua du) phai TRUE"
        );
        System.out.println("Boundary-A PASS: tuoi=22 -> TRUE");
    }

    @Test(description = "Boundary A: tuoi=21 (vua duoi nguong) -> KHONG DUOC VAY")
    public void testBoundary_A_Tuoi21() {
        Assert.assertFalse(
                VayVon.duDieuKienVay(21, 15_000_000, true, 750),
                "Boundary-A FAIL: tuoi=21 (chua du) phai FALSE"
        );
        System.out.println("Boundary-A PASS: tuoi=21 -> FALSE");
    }

    @Test(description = "Boundary B: thuNhap=10,000,000 (vua du) -> DUOC VAY (neu A,C/D hop le)")
    public void testBoundary_B_ThuNhap10tr() {
        Assert.assertTrue(
                VayVon.duDieuKienVay(25, 10_000_000.0, true, 750),
                "Boundary-B FAIL: thuNhap=10tr (vua du) phai TRUE"
        );
        System.out.println("Boundary-B PASS: thuNhap=10,000,000 -> TRUE");
    }

    @Test(description = "Boundary B: thuNhap=9,999,999 (vua duoi nguong) -> KHONG DUOC VAY")
    public void testBoundary_B_ThuNhapDuoi10tr() {
        Assert.assertFalse(
                VayVon.duDieuKienVay(25, 9_999_999.0, true, 750),
                "Boundary-B FAIL: thuNhap=9999999 (chua du) phai FALSE"
        );
        System.out.println("Boundary-B PASS: thuNhap=9,999,999 -> FALSE");
    }

    @Test(description = "Boundary D: dienTinDung=700 (bien duoi du dieu kien) -> DUOC VAY")
    public void testBoundary_D_TinDung700() {
        // Bien duoi D=True: dienTinDung chinh xac 700
        Assert.assertTrue(
                VayVon.duDieuKienVay(25, 15_000_000, false, 700),
                "Boundary-D FAIL: dienTinDung=700 (vua du) phai TRUE"
        );
        System.out.println("Boundary-D PASS: dienTinDung=700 -> TRUE");
    }

    @Test(description = "Boundary D: dienTinDung=699 (vua duoi nguong, ko co tai san) -> KHONG DUOC VAY")
    public void testBoundary_D_TinDung699() {
        Assert.assertFalse(
                VayVon.duDieuKienVay(25, 15_000_000, false, 699),
                "Boundary-D FAIL: dienTinDung=699 (khong du, ko co tai san) phai FALSE"
        );
        System.out.println("Boundary-D PASS: dienTinDung=699, khong tai san -> FALSE");
    }
}
