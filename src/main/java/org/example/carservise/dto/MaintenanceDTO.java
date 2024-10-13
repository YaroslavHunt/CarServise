package org.example.carservise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
public class MaintenanceDTO {
    private ObjectId id;
    @NotBlank
    private String name;
    private String description;
    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;
}
