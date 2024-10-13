package org.example.carservise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    @NotBlank
    private String model;
    @Positive(message = "Engine power must be greater than 0.")
    private Integer enginePower;
    @Positive(message = "Torque must be greater than 0.")
    private Integer torque;
    private String ownerUsername;
}
