package ru.jabki.filmplus.model;

public class ApiError {
    final boolean success;
    final String message;

    public ApiError(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }
    public String getMessage() {
        return this.message;
    }
}