package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Booking;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchBookingException;

import java.util.List;
import java.util.UUID;

public interface  BookingService {
    List<Booking> list();
    Booking create(Booking booking);
    List<Booking> getUserBookings(UUID userId) throws NoSuchBookingException;
    Booking cancelBooking(UUID bookingId) throws NoSuchBookingException;
}
