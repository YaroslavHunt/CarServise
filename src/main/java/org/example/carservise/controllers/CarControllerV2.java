package org.example.carservise.controllers;

import lombok.RequiredArgsConstructor;
import org.example.carservise.api.controller.CarsApi;
import org.example.carservise.api.dto.CarDTO;
import org.example.carservise.services.CarServiceV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarControllerV2 implements CarsApi {

    private final CarServiceV2 carService;

    @Override
    public ResponseEntity<List<CarDTO>> carsGet(Integer minEnginePower, Integer maxEnginePower) {
        List<CarDTO> cars = carService.carsGet(minEnginePower, maxEnginePower);
        return ResponseEntity.ok(cars);
    }

    @Override
    public ResponseEntity<Void> carsIdDelete(Long id) {
        carService.carsIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CarDTO> carsIdGet(Long id) {
        CarDTO carDTO = carService.carsIdGet(id);
        return ResponseEntity.ok(carDTO);
    }

    @Override
    public ResponseEntity<CarDTO> carsIdPut(Long id, CarDTO carDTO) {
        CarDTO updatedCar = carService.carsIdPut(id, carDTO);
        return ResponseEntity.ok(updatedCar);
    }


    @Override
    public ResponseEntity<CarDTO> carsPost(CarDTO carDTO) {
        CarDTO createdCar = carService.carsPost(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }
}

