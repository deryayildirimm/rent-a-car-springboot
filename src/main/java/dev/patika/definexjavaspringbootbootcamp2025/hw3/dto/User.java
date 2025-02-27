package dev.patika.definexjavaspringbootbootcamp2025.hw3.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String name;
    private String email;
    private String licenseNumber;
}
