package dev.patika.definexjavaspringbootbootcamp2025.hw3.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("NoSuchUserException");
    }
}
