package org.example.carservise.services;

import lombok.RequiredArgsConstructor;
import org.example.carservise.entities.Car;
import org.example.carservise.api.dto.CarDTO;
import org.example.carservise.entities.Owner;
import org.example.carservise.mappers.CarMapperV2;
import org.example.carservise.repositories.CarRepository;
import org.example.carservise.repositories.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CarServiceV2 {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final CarMapperV2 carMapper;

    public List<CarDTO> carsGet(Integer minEnginePower, Integer maxEnginePower) {
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
        return cars.stream()
                .map(carMapper::mapToDTO)
                .toList();
    }

    public CarDTO carsIdGet(Long id) {
        return carRepository.findById(id)
                .map(carMapper::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Car with id=%s not found".formatted(id)));
    }

    public CarDTO carsIdPut(Long id, CarDTO carDTO) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Car with id=%s not found".formatted(id)));

        existingCar.setModel(carDTO.getModel());
        existingCar.setEnginePower(carDTO.getEnginePower());
        existingCar.setTorque(carDTO.getTorque());

        if (carDTO.getOwnerUsername() != null) {
            Owner owner = ownerRepository.findByUsername(carDTO.getOwnerUsername())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Owner not found"));
            existingCar.setOwner(owner);
        } else {
            existingCar.setOwner(null);
        }

        Car updatedCar = carRepository.save(existingCar);
        return carMapper.mapToDTO(updatedCar);
    }


    public void carsIdDelete(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Car with id=%s not found".formatted(id));
        }
    }

    public CarDTO carsPost(CarDTO carDTO) {
        if (carDTO.getId() != null) {
            throw new IllegalArgumentException("Car cannot be created with a pre-defined id");
        }

        Car car = this.carMapper.mapToEntity(carDTO);
        carRepository.save(car);
        return this.carMapper.mapToDTO(car);
    }
}
