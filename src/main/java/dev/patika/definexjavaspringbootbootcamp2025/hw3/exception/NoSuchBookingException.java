package dev.patika.definexjavaspringbootbootcamp2025.hw3.exception;

public class NoSuchBookingException extends RuntimeException {
    public NoSuchBookingException() {
        super("NoSuchBookingException");
    }
}
