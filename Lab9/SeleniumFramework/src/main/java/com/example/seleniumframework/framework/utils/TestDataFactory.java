package com.example.seleniumframework.framework.utils;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Map;

public class TestDataFactory {

    private static final Faker faker = new Faker(new Locale("en-US"));

    public static String randomFirstName() {
        // replaceAll loại bỏ mọi ký tự không phải chữ cái — tránh apostrophe
        String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
        return name.isEmpty() ? "John" : name;
    }

    public static String randomLastName() {
        String name = faker.name().lastName().replaceAll("[^a-zA-Z]", "");
        return name.isEmpty() ? "Smith" : name;
    }

    public static String randomPostalCode() {
        // Dùng timestamp để đảm bảo luôn khác nhau mỗi lần chạy
        return String.valueOf(10000 + (int)(System.currentTimeMillis() % 89999));
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static Map<String, String> randomCheckoutData() {
        String firstName  = randomFirstName();
        String lastName   = randomLastName();
        String postalCode = randomPostalCode();

        System.out.println("[TestDataFactory] Checkout data mới sinh:");
        System.out.println("  firstName  : " + firstName);
        System.out.println("  lastName   : " + lastName);
        System.out.println("  postalCode : " + postalCode);

        return Map.of(
                "firstName",  firstName,
                "lastName",   lastName,
                "postalCode", postalCode
        );
    }
}