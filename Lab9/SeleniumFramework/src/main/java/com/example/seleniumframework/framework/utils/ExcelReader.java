package com.example.seleniumframework.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;


public class ExcelReader {


    public static Object[][] getData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException(
                        "[ExcelReader] Không tìm thấy sheet '" + sheetName
                                + "' trong file: " + filePath
                );
            }

            int lastRow = sheet.getLastRowNum();   // Index của dòng cuối
            int lastCol = sheet.getRow(0).getLastCellNum(); // Số cột từ header

            // lastRow là số dòng data (bỏ header) — mảng có lastRow phần tử
            Object[][] data = new Object[lastRow][lastCol];

            for (int r = 1; r <= lastRow; r++) { // r=1: bỏ dòng header (r=0)
                Row row = sheet.getRow(r);
                if (row == null) continue;        // Dòng trống → bỏ qua

                for (int c = 0; c < lastCol; c++) {
                    data[r - 1][c] = getCellValue(row.getCell(c));
                }
            }

            System.out.println("[ExcelReader] Đọc xong sheet '" + sheetName
                    + "': " + lastRow + " dòng data");
            return data;

        } catch (IOException e) {
            throw new RuntimeException(
                    "[ExcelReader] Lỗi đọc file: " + filePath, e
            );
        }
    }


    public static String getCellValue(Cell cell) {

        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();


            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());

            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());


            case FORMULA -> cell.getCachedFormulaResultType() == CellType.NUMERIC
                    ? String.valueOf((long) cell.getNumericCellValue())
                    : cell.getStringCellValue().trim();


            default -> "";
        };
    }
}