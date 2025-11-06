package ru.jabki.filmplus.exception;

public class UserException extends RuntimeException {
    public UserException(final String message) {
        super(message);
    }
}