package com.example.bai6_whitebox_selenium;


public class PhoneValidator {

   
    public static boolean isValid(String phone) {
        // N1: Kiem tra null hoac rong
        if (phone == null || phone.isBlank()) {         // N1
            return false;
        }

        // Loai bo khoang trang o dau/cuoi (chap nhan space)
        String trimmed = phone.trim();

        // N2: Kiem tra ky tu hop le (chi cho phep 0-9, +, space)
        if (!trimmed.matches("[0-9+\\s]+")) {            // N2
            return false;
        }

        // Chuan hoa: bo khoang trang ben trong
        String normalized = trimmed.replaceAll("\\s+", "");

        // N3: Xu ly prefix "+84"
        if (normalized.startsWith("+84")) {              // N3
            normalized = "0" + normalized.substring(3);
        } else if (normalized.startsWith("0")) {         // N4
            // Giu nguyen, da la dang chuan "0xxxxxxxxx"
        } else {
            return false;                               // N4=False: prefix sai
        }

        // N5: Kiem tra do dai phai dung 10 chu so
        if (normalized.length() != 10) {                // N5
            return false;
        }

        // N6: Kiem tra chu so thu 2 (index 1) phai la 3, 5, 7, 8, hoac 9
        char secondDigit = normalized.charAt(1);
        if (secondDigit != '3' && secondDigit != '5'    // N6
                && secondDigit != '7' && secondDigit != '8'
                && secondDigit != '9') {
            return false;
        }

        return true;  // Hop le
    }
}
