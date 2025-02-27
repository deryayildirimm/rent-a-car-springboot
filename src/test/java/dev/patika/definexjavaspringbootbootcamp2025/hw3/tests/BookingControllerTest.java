package dev.patika.definexjavaspringbootbootcamp2025.hw3.tests;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.controller.BookingController;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.Booking;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService; //  Servisi mock'luyoruz

    @Test
    void getBookings_ShouldReturnOk() throws Exception {
        mockMvc.perform(
                get("/api/booking/list"))
                .andExpect(status().isOk());
    }

    @Test
    void createBooking_ShouldReturnCreated() throws Exception {
        UUID bookingId = UUID.randomUUID();
        Booking newBooking = Booking.builder()
                .id(bookingId)
                .carId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(2))
                .status("CONFIRMED")
                .totalPrice(5000.0)
                .build();

        Mockito.when(bookingService.create(Mockito.any(Booking.class))).thenReturn(newBooking);

        String bookingJson = """
        {
            "id": "%s",
            "carId": "%s",
            "userId": "%s",
            "startDate": "%s",
            "endDate": "%s",
            "status": "CONFIRMED",
            "totalPrice": 5000.0
        }
        """.formatted(
                bookingId,
                newBooking.getCarId(),
                newBooking.getUserId(),
                newBooking.getStartDate(),
                newBooking.getEndDate()
        );

        mockMvc.perform(post("/api/booking")
                        .contentType("application/json")
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void getUserBookings_ShouldReturnOk() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Booking> userBookings = List.of(
                Booking.builder()
                        .id(UUID.randomUUID())
                        .carId(UUID.randomUUID())
                        .userId(userId)
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(3))
                        .status("CONFIRMED")
                        .totalPrice(4500.0)
                        .build()
        );

        Mockito.when(bookingService.getUserBookings(userId)).thenReturn(userBookings);

        mockMvc.perform(get("/api/booking/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("CONFIRMED"));
    }

    @Test
    void getUserBookings_ShouldReturnBadRequest() throws Exception {
        String invalidUserId = "invalid-uuid";

        mockMvc.perform(get("/api/booking/user/" + invalidUserId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelBooking_ShouldReturnOk() throws Exception {
        UUID bookingId = UUID.randomUUID();
        Booking cancelledBooking = Booking.builder()
                .id(bookingId)
                .carId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .status("CANCELLED")
                .totalPrice(0.0)
                .build();

        Mockito.when(bookingService.cancelBooking(bookingId)).thenReturn(cancelledBooking);

        mockMvc.perform(put("/api/booking/" + bookingId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void cancelBooking_ShouldReturnBadRequest() throws Exception {
        String nonExistingBookingId = "invalid-uuid"; //  Geçersiz UUID formatı!

        mockMvc.perform(put("/api/booking/" + nonExistingBookingId + "/cancel"))
                .andExpect(status().isBadRequest()); //  400 Bad Request bekliyoruz
    }
}
