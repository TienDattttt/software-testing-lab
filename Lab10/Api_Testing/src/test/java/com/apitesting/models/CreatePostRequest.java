package com.apitesting.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("userId")
    private int userId;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return userId;
    }
}
