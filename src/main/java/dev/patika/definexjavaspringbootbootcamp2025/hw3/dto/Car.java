package dev.patika.definexjavaspringbootbootcamp2025.hw3.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Car {
    private UUID id;
    private String model;
    private String brand;
    private Integer year;
    private String licensePlate;
    private Double dailyRate;
    private Boolean available;
}
