package dev.patika.definexjavaspringbootbootcamp2025.hw3.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import java.time.LocalDateTime;

@Data
@Builder
public class Booking {
    private UUID id;
    private UUID carId;
    private UUID userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Double totalPrice;
}
