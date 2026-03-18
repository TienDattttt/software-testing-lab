package com.example.seleniumframework.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonReader {

    // ObjectMapper là thread-safe, tạo một lần dùng lại nhiều lần
    private static final ObjectMapper mapper = new ObjectMapper();


    public static List<UserData> readUsers(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException(
                        "[JsonReader] Không tìm thấy file: " + filePath
                );
            }

            List<UserData> users = mapper.readValue(
                    file,
                    new TypeReference<List<UserData>>() {}
            );
            System.out.println("[JsonReader] Đọc xong: " + users.size()
                    + " users từ " + filePath);
            return users;
        } catch (IOException e) {
            throw new RuntimeException(
                    "[JsonReader] Lỗi đọc file JSON: " + filePath, e
            );
        }
    }
}