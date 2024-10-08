package org.example.carservise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    @NotNull
    private String model;
    @Positive(message = "Engine power must be greater than 0.")
    private Integer enginePower;
    @Positive(message = "Torque must be greater than 0.")
    private Integer torque;
}
