package org.example.carservise.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.example.carservise.entities.Owner;
import org.example.carservise.mappers.CarMapper;
import org.example.carservise.repositories.CarRepository;
import org.example.carservise.repositories.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
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
        return carRepository.findById(id)
                .map(carMapper::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Car with id=%s not found".formatted(id)));
    }

    public CarDTO createCar(CarDTO carDTO) {
        Owner owner = null;
        if (carDTO.getOwnerUsername() != null) {
            owner = ownerRepository.findByUsername(carDTO.getOwnerUsername())
                    .orElseThrow(() -> new ResponseStatusException(
                            BAD_REQUEST,
                            "Owner with username '%s' not found".formatted(carDTO.getOwnerUsername())
                    ));
        }
        Car car = carMapper.mapToEntity(carDTO, owner);
        return carMapper.mapToDTO(carRepository.save(car));
    }

    @Transactional
    public CarDTO updateCar(Long carId, Car newCar) {
        Car existingCar = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car with id=%s not found".formatted(carId)));
        existingCar.setModel(newCar.getModel());
        existingCar.setEnginePower(newCar.getEnginePower());
        existingCar.setTorque(newCar.getTorque());
        if (newCar.getOwner() != null) {
            Owner owner = ownerRepository.findById(newCar.getOwner().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
            existingCar.setOwner(owner);
        } else {
            existingCar.setOwner(null);
        }
        Car updatedCar = carRepository.save(existingCar);
        return carMapper.mapToDTO(updatedCar);
    }

    public void deleteCar(Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
        } else {
            throw new IllegalArgumentException("Car with id=%s not found".formatted(carId));
        }
    }
}
