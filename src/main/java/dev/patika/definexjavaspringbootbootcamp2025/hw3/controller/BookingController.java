package dev.patika.definexjavaspringbootbootcamp2025.hw3.controller;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Booking;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchBookingException;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(bookingService.list());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.create(booking), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable UUID userId) {
        return handleExceptions(() -> ResponseEntity.ok(bookingService.getUserBookings(userId)));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID id) {
        return handleExceptions(() -> ResponseEntity.ok(bookingService.cancelBooking(id)));
    }

    private <T> ResponseEntity<T> handleExceptions(Supplier<ResponseEntity<T>> supplier) {
        try {
            return supplier.get();
        } catch (NoSuchBookingException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
