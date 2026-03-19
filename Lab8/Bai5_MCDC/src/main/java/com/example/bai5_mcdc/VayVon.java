package com.example.bai5_mcdc;


public class VayVon {

    
    public static boolean duDieuKienVay(int tuoi, double thuNhap,
                                         boolean coTaiSanBaoLanh, int dienTinDung) {
        boolean dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000);   // A && B


        boolean dieuKienBaoDam = coTaiSanBaoLanh || (dienTinDung >= 700);   // C || D

        return dieuKienCoBan && dieuKienBaoDam;
    }
}
