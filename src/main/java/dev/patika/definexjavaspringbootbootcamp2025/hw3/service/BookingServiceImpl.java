package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Booking;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchBookingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    private final List<Booking> bookingArrayList = new ArrayList<>();
    @Override
    public List<Booking> list() {
        return new ArrayList<>(bookingArrayList);
    }
    @Override
    public Booking create(Booking booking) {
        Booking newBooking = Booking.builder()
                .id(UUID.randomUUID())
                .carId(booking.getCarId())
                .userId(booking.getUserId())
                .endDate(booking.getEndDate())
                .startDate(booking.getStartDate())
                .status("CONFIRMED")
                .totalPrice(booking.getTotalPrice())
                .build();

        bookingArrayList.add(newBooking);
        return newBooking;
    }

    @Override
    public List<Booking> getUserBookings(UUID userId) throws NoSuchBookingException {

        return Optional.of(bookingArrayList.stream()
                        .filter(b -> b.getUserId().equals(userId))
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(NoSuchBookingException::new);
    }

    @Override
    public Booking cancelBooking(UUID bookingId) throws NoSuchBookingException {

        Booking booking = bookingArrayList.stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(NoSuchBookingException::new);

        booking.setStatus("CANCELLED");
        return booking;
    }
}
