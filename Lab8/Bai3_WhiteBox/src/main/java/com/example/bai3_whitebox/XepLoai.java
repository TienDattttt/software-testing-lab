package com.example.bai3_whitebox;

public class XepLoai {


    public static String xepLoai(double diemTB, boolean coThiLai) {
        if (diemTB < 0 || diemTB > 10) {           // Dieu kien 1 (N1)
            return "Diem khong hop le";
        }
        if (diemTB >= 8.5) {                         // Dieu kien 2 (N2)
            return "Gioi";
        } else if (diemTB >= 7.0) {                  // Dieu kien 3 (N3)
            return "Kha";
        } else if (diemTB >= 5.5) {                  // Dieu kien 4 (N4)
            return "Trung Binh";
        } else {
            if (coThiLai) {                          // Dieu kien 5 (N5)
                return "Thi lai";
            }
            return "Yeu - Hoc lai";
        }
    }
}
