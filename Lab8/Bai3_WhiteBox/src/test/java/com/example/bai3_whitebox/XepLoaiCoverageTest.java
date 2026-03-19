package com.example.bai3_whitebox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class XepLoaiCoverageTest {



    @Test(description = "TC-S1 [Statement]: diemTB=-1 (am) -> 'Diem khong hop le' | Phu: N1-True")
    public void testStatement_DiemAm() {
        String ketQua = XepLoai.xepLoai(-1, false);
        Assert.assertEquals(ketQua, "Diem khong hop le",
                "TC-S1 FAIL: diem am phai tra ve 'Diem khong hop le'");
        System.out.println("TC-S1 PASS: xepLoai(-1, false) = '" + ketQua + "' | Node N1=True");
    }

    @Test(description = "TC-S2 [Statement]: diemTB=11 (lon hon 10) -> 'Diem khong hop le' | Phu: N1-True (bien)")
    public void testStatement_DiemLonHon10() {
        String ketQua = XepLoai.xepLoai(11, false);
        Assert.assertEquals(ketQua, "Diem khong hop le",
                "TC-S2 FAIL: diem > 10 phai tra ve 'Diem khong hop le'");
        System.out.println("TC-S2 PASS: xepLoai(11, false) = '" + ketQua + "' | Node N1=True (bien tren)");
    }

    @Test(description = "TC-S3 [Statement]: diemTB=9.0 -> 'Gioi' | Phu: N1-False, N2-True")
    public void testStatement_Gioi() {
        String ketQua = XepLoai.xepLoai(9.0, false);
        Assert.assertEquals(ketQua, "Gioi",
                "TC-S3 FAIL: diem=9.0 phai la 'Gioi'");
        System.out.println("TC-S3 PASS: xepLoai(9.0, false) = '" + ketQua + "' | Path: N1=F -> N2=T");
    }

    @Test(description = "TC-S4 [Statement]: diemTB=7.5 -> 'Kha' | Phu: N1-False, N2-False, N3-True")
    public void testStatement_Kha() {
        String ketQua = XepLoai.xepLoai(7.5, false);
        Assert.assertEquals(ketQua, "Kha",
                "TC-S4 FAIL: diem=7.5 phai la 'Kha'");
        System.out.println("TC-S4 PASS: xepLoai(7.5, false) = '" + ketQua + "' | Path: N1=F -> N2=F -> N3=T");
    }

    @Test(description = "TC-S5 [Statement]: diemTB=6.0 -> 'Trung Binh' | Phu: N1-F, N2-F, N3-F, N4-True")
    public void testStatement_TrungBinh() {
        String ketQua = XepLoai.xepLoai(6.0, false);
        Assert.assertEquals(ketQua, "Trung Binh",
                "TC-S5 FAIL: diem=6.0 phai la 'Trung Binh'");
        System.out.println("TC-S5 PASS: xepLoai(6.0, false) = '" + ketQua + "' | Path: N3=F -> N4=T");
    }

    @Test(description = "TC-S6 [Statement]: diemTB=4.0, coThiLai=true -> 'Thi lai' | Phu: N5-True")
    public void testStatement_ThiLai() {
        String ketQua = XepLoai.xepLoai(4.0, true);
        Assert.assertEquals(ketQua, "Thi lai",
                "TC-S6 FAIL: diem=4.0 va coThiLai=true phai la 'Thi lai'");
        System.out.println("TC-S6 PASS: xepLoai(4.0, true) = '" + ketQua + "' | Path: N4=F -> N5=T");
    }



    @Test(description = "TC-B1 [Branch N1-True]: diem=-1 -> N1 du dung, thoat ngay | 'Diem khong hop le'")
    public void testBranch_N1_True_DiemAm() {
        Assert.assertEquals(XepLoai.xepLoai(-1, false), "Diem khong hop le",
                "TC-B1 FAIL: N1-True with diem=-1");
        System.out.println("TC-B1 PASS: N1-True | diem=-1 -> 'Diem khong hop le'");
    }

    @Test(description = "TC-B2 [Branch N1-False, N2-True]: diem=8.5 -> bien duoi cua 'Gioi'")
    public void testBranch_N2_True_Gioi_BienDuoi() {
        // Bien duoi cua Gioi: diemTB = 8.5 (chinh xac)
        Assert.assertEquals(XepLoai.xepLoai(8.5, false), "Gioi",
                "TC-B2 FAIL: N2-True with diem=8.5 (bien duoi Gioi)");
        System.out.println("TC-B2 PASS: N1=F, N2=T | diem=8.5 -> 'Gioi'");
    }

    @Test(description = "TC-B3 [Branch N2-False, N3-True]: diem=7.0 -> bien duoi cua 'Kha'")
    public void testBranch_N3_True_Kha_BienDuoi() {
        // Bien duoi cua Kha: diemTB = 7.0
        Assert.assertEquals(XepLoai.xepLoai(7.0, false), "Kha",
                "TC-B3 FAIL: N3-True with diem=7.0 (bien duoi Kha)");
        System.out.println("TC-B3 PASS: N2=F, N3=T | diem=7.0 -> 'Kha'");
    }

    @Test(description = "TC-B4 [Branch N3-False, N4-True]: diem=5.5 -> bien duoi cua 'Trung Binh'")
    public void testBranch_N4_True_TrungBinh_BienDuoi() {
        // Bien duoi cua Trung Binh: diemTB = 5.5
        Assert.assertEquals(XepLoai.xepLoai(5.5, false), "Trung Binh",
                "TC-B4 FAIL: N4-True with diem=5.5 (bien duoi Trung Binh)");
        System.out.println("TC-B4 PASS: N3=F, N4=T | diem=5.5 -> 'Trung Binh'");
    }

    @Test(description = "TC-B5 [Branch N4-False, N5-True]: diem=4.0, coThiLai=true -> 'Thi lai'")
    public void testBranch_N5_True_ThiLai() {
        Assert.assertEquals(XepLoai.xepLoai(4.0, true), "Thi lai",
                "TC-B5 FAIL: N5-True with coThiLai=true");
        System.out.println("TC-B5 PASS: N4=F, N5=T | diem=4.0, coThiLai=true -> 'Thi lai'");
    }

    @Test(description = "TC-B6 [Branch N5-False]: diem=4.0, coThiLai=false -> 'Yeu - Hoc lai'")
    public void testBranch_N5_False_YeuHocLai() {
        Assert.assertEquals(XepLoai.xepLoai(4.0, false), "Yeu - Hoc lai",
                "TC-B6 FAIL: N5-False with coThiLai=false");
        System.out.println("TC-B6 PASS: N4=F, N5=F | diem=4.0, coThiLai=false -> 'Yeu - Hoc lai'");
    }



    @Test(description = "Boundary: diem=0 (bien duoi hop le) -> khong phai Gioi/Kha/TrungBinh")
    public void testBoundary_DiemZero() {
        String ketQua = XepLoai.xepLoai(0, false);
        Assert.assertEquals(ketQua, "Yeu - Hoc lai",
                "Boundary FAIL: diem=0 la hop le, phai la 'Yeu - Hoc lai'");
        System.out.println("Boundary PASS: xepLoai(0, false) = '" + ketQua + "'");
    }

    @Test(description = "Boundary: diem=10 (bien tren hop le) -> 'Gioi'")
    public void testBoundary_DiemMuoi() {
        String ketQua = XepLoai.xepLoai(10, false);
        Assert.assertEquals(ketQua, "Gioi",
                "Boundary FAIL: diem=10 phai la 'Gioi'");
        System.out.println("Boundary PASS: xepLoai(10, false) = '" + ketQua + "'");
    }

    @Test(description = "Boundary: diem=8.49 (ngay duoi nguong Gioi) -> 'Kha'")
    public void testBoundary_VuaKhongDatGioi() {
        String ketQua = XepLoai.xepLoai(8.49, false);
        Assert.assertEquals(ketQua, "Kha",
                "Boundary FAIL: diem=8.49 phai la 'Kha', khong phai 'Gioi'");
        System.out.println("Boundary PASS: xepLoai(8.49, false) = '" + ketQua + "' (vua duoi nguong Gioi)");
    }
}
