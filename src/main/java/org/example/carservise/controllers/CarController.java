package org.example.carservise.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.carservise.entities.Car;
import org.example.carservise.repositories.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Car> getCars(@RequestParam(required = false) Integer minEnginePower,
                             @RequestParam(required = false) Integer maxEnginePower) {
        if (minEnginePower != null && maxEnginePower != null) {
            return carRepository.findByEnginePowerBetween(minEnginePower, maxEnginePower);
        } else if (minEnginePower != null) {
            return carRepository.findByEnginePowerGreaterThanEqual(minEnginePower);
        } else if (maxEnginePower != null) {
            return carRepository.findByEnginePowerLessThanEqual(maxEnginePower);
        } else {
            return carRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return ResponseEntity.of(carRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carRepository.save(car));
    }

    @Transactional
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Car> updateCar(@RequestBody Car newCar, @PathVariable(name = "id") Long carId) {
        return ResponseEntity.of(
                carRepository
                        .findById(carId)
                        .map(updatingCar -> {
                            updatingCar.setModel(newCar.getModel());
                            updatingCar.setEnginePower(newCar.getEnginePower());
//                            return carRepository.save(updatingCar); //save is not required as there is @Transactional
                            return updatingCar;
                        })
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable(name = "id") Long carId) {
        carRepository.deleteById(carId);
        return ResponseEntity.ok().build();
    }
}

