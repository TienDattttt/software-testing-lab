package com.example.seleniumframework.framework.utils;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserData {

    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;

    @JsonProperty("role")
    public String role;

    @JsonProperty("expectSuccess")
    public boolean expectSuccess;

    @JsonProperty("description")
    public String description;

    // ToString để in log dễ đọc
    @Override
    public String toString() {
        return String.format("UserData{username='%s', role='%s', expectSuccess=%s, desc='%s'}",
                username, role, expectSuccess, description);
    }
}