package com.loplyy.subscriber_service.controller;

public class ApiResponse<T>{
    private boolean success;
    private String message;
    private int statusCode;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, int statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Successfully", data, 200);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(false, "Error", null, 500);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
