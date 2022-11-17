package com.example.demo2.dto;


import lombok.Data;

@Data
public class TestRequestBodyDTO {
    private static int id;
    private static String message;

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
