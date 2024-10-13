package org.example.carservise.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.example.carservise.mappers.CarMapper;
import org.example.carservise.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public List<CarDTO> getCars(Integer minEnginePower, Integer maxEnginePower) {
        List<Car> cars;
        if (minEnginePower != null && maxEnginePower != null) {
            cars = carRepository.findByEnginePowerBetween(minEnginePower, maxEnginePower);
        } else if (minEnginePower != null) {
            cars = carRepository.findByEnginePowerGreaterThanEqual(minEnginePower);
        } else if (maxEnginePower != null) {
            cars = carRepository.findByEnginePowerLessThanEqual(maxEnginePower);
        } else {
            cars = carRepository.findAll();
        }
        return cars.stream().map(carMapper::mapToDTO).toList();
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car with id=%s not found".formatted(id)));
        return carMapper.mapToDTO(car);
    }

    public CarDTO createCar(CarDTO carDTO) {
        Car car = carMapper.mapToEntity(carDTO);
        Car savedCar = carRepository.save(car);
        return carMapper.mapToDTO(savedCar);
    }

    @Transactional
    public CarDTO updateCar(Long carId, Car newCar) {
        return carRepository.findById(carId)
                .map(updatingCar -> {
                    updatingCar.setModel(newCar.getModel());
                    updatingCar.setEnginePower(newCar.getEnginePower());
                    updatingCar.setTorque(newCar.getTorque());
                    return carMapper.mapToDTO(updatingCar);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car with id=%s not found".formatted(carId)));
    }

    public void deleteCar(Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
        } else {
            throw new IllegalArgumentException("Car with id=%s not found".formatted(carId));
        }
    }
}
