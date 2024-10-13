package org.example.carservise.controllers;

import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.example.carservise.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getCars(@RequestParam(required = false) Integer minEnginePower,
                                                @RequestParam(required = false) Integer maxEnginePower) {
        List<CarDTO> cars = carService.getCars(minEnginePower, maxEnginePower);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        CarDTO carDTO = carService.getCarById(id);
        return ResponseEntity.ok(carDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<CarDTO> updateCar(@RequestBody Car newCar, @PathVariable Long id) {
        CarDTO updatedCarDTO = carService.updateCar(id, newCar);
        return ResponseEntity.ok(updatedCarDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

}

