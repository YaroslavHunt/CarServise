package org.example.carservise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull
    private String model;
    @Positive(message = "Engine power must be greater than 0.")
    private Integer enginePower;
    @Positive(message = "Torque must be greater than 0.")
    private Integer torque;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;
}
