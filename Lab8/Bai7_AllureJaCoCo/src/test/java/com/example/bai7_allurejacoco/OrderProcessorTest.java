package com.example.bai7_allurejacoco;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Epic("Bai7 - Tich hop Allure + JaCoCo")
@Feature("He thong xu ly don hang thuong mai dien tu")
public class OrderProcessorTest {

    private OrderProcessor processor;

    // -- Helper: tao List<Item> tu cac gia tri price --
    private List<Item> items(double... prices) {
        List<Item> list = new java.util.ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            list.add(new Item("Product" + (i + 1), prices[i]));
        }
        return list;
    }

    @BeforeMethod
    public void setUp() {
        processor = new OrderProcessor();
    }



    @Test(description = "P1 [D1=True]: Gio hang null -> throws IllegalArgumentException")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 1: D1=True. items=null nen throw exception ngay. " +
                 "Kiem tra D1: items == null || items.isEmpty()")
    public void testP1_GioHangNull() {
        Allure.step("Chuan bi: items=null");
        Allure.step("Goi calculateTotal voi items=null, ky vong throw exception");
        try {
            processor.calculateTotal(null, null, "", "COD");
            Assert.fail("P1 FAIL: items=null phai throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("Gio hang trong"),
                    "P1 FAIL: message exception phai la 'Gio hang trong'");
        }
        System.out.println("P1 PASS [D1=True]: items=null -> throws IllegalArgumentException");
    }

    @Test(description = "P1b [D1=True]: Gio hang rong -> throws IllegalArgumentException")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 1b: D1=True voi items la list rong. isEmpty()=true.")
    public void testP1b_GioHangRong() {
        Allure.step("Chuan bi: items = danh sach rong");
        try {
            processor.calculateTotal(Collections.emptyList(), null, "", "COD");
            Assert.fail("P1b FAIL: items rong phai throws IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("Gio hang trong"));
        }
        System.out.println("P1b PASS [D1=True]: items=[] -> throws exception");
    }

    @Test(description = "P2 [D2=F,D5=F,D6=F,D7=F]: Khong coupon, khong member, tong>=500k -> khong ship")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 2: D1=F,D2=F,D5=F,D6=F,D7=F. subtotal=600k, khong giam gia, tong>=500k.")
    public void testP2_KhongCoupon_KhongMember_KhongShip() {
        Allure.step("items=[600k], coupon=null, member=SILVER, payment=BANKING");
        double result = processor.calculateTotal(
                items(600_000),
                null, "SILVER", "BANKING");
        Allure.step("Kiem tra ket qua = 600,000");
        Assert.assertEquals(result, 600_000.0, 0.01,
                "P2 FAIL: khong giam gia, tong >= 500k, khong phi ship");
        System.out.println("P2 PASS [D2=F,D7=F]: total=600000");
    }

    @Test(description = "P3 [D2=F,D5=F,D6=F,D7=T,D8=T]: Tong<500k, ONLINE -> phi ship 30k")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 3: D7=True, D8=True. total=200k < 500k, payment=BANKING -> +30k.")
    public void testP3_Online_PhiShip30k() {
        Allure.step("items=[200k], coupon=null, member='', payment=BANKING");
        double result = processor.calculateTotal(
                items(200_000),
                null, "", "BANKING");
        // 200_000 + 30_000 = 230_000
        Allure.step("Kiem tra ket qua = 230,000 (200k + 30k ship online)");
        Assert.assertEquals(result, 230_000.0, 0.01,
                "P3 FAIL: online payment tong < 500k phai them 30k ship");
        System.out.println("P3 PASS [D7=T,D8=T]: total=230000");
    }

    @Test(description = "P4 [D7=T,D8=F]: COD, tong<500k -> phi ship 20k")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 4: D7=True, D8=False (COD). total=200k < 500k, COD -> +20k.")
    public void testP4_COD_PhiShip20k() {
        Allure.step("items=[200k], coupon=null, member='', payment=COD");
        double result = processor.calculateTotal(
                items(200_000),
                null, "", "COD");
        // 200_000 + 20_000 = 220_000
        Allure.step("Kiem tra ket qua = 220,000 (200k + 20k ship COD)");
        Assert.assertEquals(result, 220_000.0, 0.01,
                "P4 FAIL: COD payment tong < 500k phai them 20k ship");
        System.out.println("P4 PASS [D7=T,D8=F]: total=220000");
    }

    @Test(description = "P5 [D2=T,D3=T,D7=T,D8=T]: SALE10 giam 10%, online -> phi ship 30k")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 5: D2=T, D3=T (SALE10). subtotal=300k, discount=30k, total=270k+30k=300k.")
    public void testP5_Sale10_Online() {
        Allure.step("items=[300k], coupon=SALE10, member='', payment=BANKING");
        double result = processor.calculateTotal(
                items(300_000),
                "SALE10", "", "BANKING");
        // subtotal=300k, discount=10%=30k, total=270k < 500k -> +30k = 300k
        Allure.step("Kiem tra: 300k - 10% + 30k ship = 300,000");
        Assert.assertEquals(result, 300_000.0, 0.01,
                "P5 FAIL: SALE10 + online: 300k-30k+30k=300k");
        System.out.println("P5 PASS [D3=T,D8=T]: SALE10 subtotal=300k -> total=300000");
    }

    @Test(description = "P6 [D2=T,D3=F,D4=T,D7=T,D8=T]: SALE20 giam 20%, online")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 6: D2=T, D3=F, D4=T (SALE20). subtotal=300k, discount=60k, total=240k+30k=270k.")
    public void testP6_Sale20_Online() {
        Allure.step("items=[300k], coupon=SALE20, member='', payment=BANKING");
        double result = processor.calculateTotal(
                items(300_000),
                "SALE20", "", "BANKING");
        // subtotal=300k, discount=20%=60k, total=240k < 500k -> +30k = 270k
        Allure.step("Kiem tra: 300k - 20% + 30k ship = 270,000");
        Assert.assertEquals(result, 270_000.0, 0.01,
                "P6 FAIL: SALE20 + online: 300k-60k+30k=270k");
        System.out.println("P6 PASS [D4=T,D8=T]: SALE20 subtotal=300k -> total=270000");
    }

    @Test(description = "P7 [D2=T,D3=F,D4=F]: Ma giam gia khong hop le -> throws exception")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Path 7: D2=T, D3=F, D4=F. coupon='INVALID' -> throws exception.")
    public void testP7_CouponKhongHopLe() {
        Allure.step("items=[100k], coupon='INVALID' -> ky vong throw exception");
        try {
            processor.calculateTotal(items(100_000), "INVALID", "", "COD");
            Assert.fail("P7 FAIL: coupon khong hop le phai throws exception");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("Ma giam gia khong hop le"),
                    "P7 FAIL: message exception sai");
        }
        System.out.println("P7 PASS [D3=F,D4=F]: coupon=INVALID -> throws exception");
    }

    @Test(description = "P8 [D5=T,D7=F]: Khach GOLD giam 5%, tong>=500k -> khong ship")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 8: D5=True. member=GOLD, subtotal=600k, memberDiscount=30k, total=570k.")
    public void testP8_GoldMember_KhongShip() {
        Allure.step("items=[600k], coupon=null, member=GOLD, payment=COD");
        double result = processor.calculateTotal(
                items(600_000),
                null, "GOLD", "COD");
        // subtotal=600k, memberDiscount=5%=30k, total=570k >= 500k -> khong ship
        Allure.step("Kiem tra: 600k - 5% = 570,000");
        Assert.assertEquals(result, 570_000.0, 0.01,
                "P8 FAIL: GOLD member 600k: 600k-30k=570k, khong phi ship");
        System.out.println("P8 PASS [D5=T,D7=F]: GOLD member 600k -> total=570000");
    }

    @Test(description = "P9 [D6=T,D7=F]: Khach PLATINUM giam 10%, tong>=500k -> khong ship")
    @Story("Basis Path Testing - CC = 9")
    @Severity(SeverityLevel.NORMAL)
    @Description("Path 9: D5=F, D6=True. member=PLATINUM, subtotal=600k, memberDiscount=60k, total=540k.")
    public void testP9_PlatinumMember_KhongShip() {
        Allure.step("items=[600k], coupon=null, member=PLATINUM, payment=COD");
        double result = processor.calculateTotal(
                items(600_000),
                null, "PLATINUM", "COD");
        // subtotal=600k, memberDiscount=10%=60k, total=540k >= 500k -> khong ship
        Allure.step("Kiem tra: 600k - 10% = 540,000");
        Assert.assertEquals(result, 540_000.0, 0.01,
                "P9 FAIL: PLATINUM member 600k: 600k-60k=540k, khong phi ship");
        System.out.println("P9 PASS [D6=T,D7=F]: PLATINUM member 600k -> total=540000");
    }



    @Test(description = "MC/DC M1 [D2=T,D3=T]: coupon='SALE10', hop le -> ap dung giam gia 10%")
    @Story("MC/DC Analysis - D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row M1: D2=True (coupon co gia tri), D3=True (la SALE10). " +
                 "Chung minh D2 doc lap (cap M1 vs M3) va D3 doc lap (cap M1 vs M2).")
    public void testMCDC_M1_D2True_D3True_Sale10() {
        Allure.step("M1: coupon=SALE10 (D2=T, D3=T) -> ap dung 10% discount");
        // items=[1000k], SALE10, PLATINUM, COD
        // subtotal=1000k, discount=100k, memberDiscount=0, total=900k >= 500k
        double result = processor.calculateTotal(
                items(1_000_000),
                "SALE10", "", "COD");
        Assert.assertEquals(result, 900_000.0, 0.01,
                "MC/DC M1 FAIL: SALE10 on 1000k = 900k");
        System.out.println("MC/DC M1 PASS: SALE10 1000k -> 900000 [D2=T,D3=T]");
    }

    @Test(description = "MC/DC M2 [D2=T,D3=F,D4=T]: coupon='SALE20', D3=False -> ap dung D4 (20%)")
    @Story("MC/DC Analysis - D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row M2: D2=True, D3=False (khong phai SALE10). " +
                 "Chung minh B(D3) doc lap: capping M1(D3=T->apply SALE10) vs M2(D3=F->D4).")
    public void testMCDC_M2_D2True_D3False_Sale20() {
        Allure.step("M2: coupon=SALE20 (D2=T, D3=F, D4=T) -> ap dung 20% discount");
        double result = processor.calculateTotal(
                items(1_000_000),
                "SALE20", "", "COD");
        Assert.assertEquals(result, 800_000.0, 0.01,
                "MC/DC M2 FAIL: SALE20 on 1000k = 800k");
        System.out.println("MC/DC M2 PASS: SALE20 1000k -> 800000 [D2=T,D3=F]");
    }

    @Test(description = "MC/DC M3 [D2=F]: coupon=null -> D2=False, khong ap dung giam gia")
    @Story("MC/DC Analysis - D2 && D3 (Coupon)")
    @Severity(SeverityLevel.CRITICAL)
    @Description("MC/DC Row M3: D2=False (coupon=null). " +
                 "Chung minh A(D2) doc lap: M1(D2=T->apply) vs M3(D2=F->skip coupon).")
    public void testMCDC_M3_D2False_NoCoupon() {
        Allure.step("M3: coupon=null (D2=F) -> khong co giam gia");
        double result = processor.calculateTotal(
                items(1_000_000),
                null, "", "COD");
        Assert.assertEquals(result, 1_000_000.0, 0.01,
                "MC/DC M3 FAIL: khong coupon 1000k: khong giam, khong ship = 1000k");
        System.out.println("MC/DC M3 PASS: coupon=null 1000k -> 1000000 [D2=F]");
    }

    @Test(description = "MC/DC M3b [D2=F bien]: coupon='' (rong) -> D2=False")
    @Story("MC/DC Analysis - D2 && D3 (Coupon)")
    @Severity(SeverityLevel.MINOR)
    @Description("MC/DC Row M3b: coupon='' (chuoi rong). !couponCode.isEmpty()=False -> D2=False.")
    public void testMCDC_M3b_D2False_EmptyCoupon() {
        Allure.step("M3b: coupon='' (rong) -> D2=False (C2=isEmpty=True)");
        double result = processor.calculateTotal(
                items(1_000_000),
                "", "", "COD");
        Assert.assertEquals(result, 1_000_000.0, 0.01,
                "MC/DC M3b FAIL: coupon='' khong giam gia");
        System.out.println("MC/DC M3b PASS: coupon='' 1000k -> 1000000 [C2=empty]");
    }


    @DataProvider(name = "member_discount_data")
    public Object[][] memberDiscountData() {
        return new Object[][] {
            // subtotal, coupon, member, payment, expected, mo_ta
            { 600_000.0, null, "GOLD",     "COD",     570_000.0, "GOLD -5%, >=500k, no ship"     },
            { 600_000.0, null, "PLATINUM", "COD",     540_000.0, "PLATINUM -10%, >=500k, no ship" },
            { 300_000.0, null, "GOLD",     "COD",     265_000.0, "GOLD 300k: 285k+20k ship COD"   },
            { 300_000.0, null, "PLATINUM", "BANKING", 240_000.0, "PLATINUM 300k: 270k-30k+... Wait" },
        };
    }


    @Test(description = "Boundary D7: total=500,000 (dung nguong) -> KHONG phi ship")
    @Story("Boundary Value Testing")
    @Severity(SeverityLevel.MINOR)
    @Description("Kiem tra bien: total chinh xac 500k. D7: total < 500000 la FALSE -> khong phi ship.")
    public void testBoundary_D7_Total500k_KhongShip() {
        Allure.step("items=[500k], khong giam gia, payment=COD");
        double result = processor.calculateTotal(
                items(500_000), null, "", "COD");
        Assert.assertEquals(result, 500_000.0, 0.01,
                "Boundary D7 FAIL: total=500k (khong < 500k) phai khong phi ship");
        System.out.println("Boundary D7 PASS: total=500000 (bien), D7=False -> khong phi ship");
    }

    @Test(description = "Boundary D7: total=499,999 (vua duoi nguong) -> CO phi ship")
    @Story("Boundary Value Testing")
    @Severity(SeverityLevel.MINOR)
    @Description("Kiem tra bien: total=499999 < 500000. D7=True -> phai tinh phi ship.")
    public void testBoundary_D7_Total499999_CoShip() {
        Allure.step("items=[499999], payment=COD -> D7=True -> +20k");
        double result = processor.calculateTotal(
                items(499_999), null, "", "COD");
        Assert.assertEquals(result, 519_999.0, 0.01,
                "Boundary D7 FAIL: total=499999 < 500k phai co phi ship COD +20k");
        System.out.println("Boundary D7 PASS: total=499999, D7=True -> +20k = 519999");
    }

    @Test(description = "Ket hop: SALE10 + GOLD member + COD, tong >= 500k")
    @Story("Integration Test - Coupon + Member + Ship")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ket hop: SALE10 giam 10% + GOLD giam 5% them. subtotal=1000k. " +
                 "discount=100k, after_coupon=900k, memberDiscount=45k, total=855k>=500k.")
    public void testKetHop_Sale10_Gold_KhongShip() {
        Allure.step("items=[1000k], SALE10, GOLD, COD");
        double result = processor.calculateTotal(
                items(1_000_000), "SALE10", "GOLD", "COD");

        Allure.step("Kiem tra: 1000k - 100k(SALE10) - 45k(GOLD) = 855,000");
        Assert.assertEquals(result, 855_000.0, 0.01,
                "KetHop FAIL: SALE10+GOLD+1000k: 1000-100-45=855k");
        System.out.println("KetHop PASS: SALE10+GOLD 1000k -> 855000");
    }

    @Test(description = "Ket hop: SALE20 + PLATINUM + BANKING, tong < 500k")
    @Story("Integration Test - Coupon + Member + Ship")
    @Severity(SeverityLevel.NORMAL)
    @Description("SALE20 giam 20% + PLATINUM giam 10%. subtotal=400k, discount=80k, " +
                 "after_coupon=320k, memberDiscount=32k, total=288k<500k, ship BANKING +30k = 318k.")
    public void testKetHop_Sale20_Platinum_Online() {
        Allure.step("items=[400k], SALE20, PLATINUM, BANKING");
        double result = processor.calculateTotal(
                items(400_000), "SALE20", "PLATINUM", "BANKING");
        Allure.step("Kiem tra: 400k-80k(SALE20)-32k(PLATINUM)+30k(ship)=318,000");
        Assert.assertEquals(result, 318_000.0, 0.01,
                "KetHop FAIL: SALE20+PLATINUM+400k+BANKING = 318k");
        System.out.println("KetHop PASS: SALE20+PLATINUM+BANKING 400k -> 318000");
    }
}
