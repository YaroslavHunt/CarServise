package org.example.carservise.mappers;

import org.example.carservise.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.example.carservise.entities.Owner;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToEntity(CarDTO carDTO, Owner owner) {
        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setEnginePower(carDTO.getEnginePower());
        car.setTorque(carDTO.getTorque());
        car.setOwner(owner);
        return car;
    }

    public CarDTO mapToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setModel(car.getModel());
        dto.setEnginePower(car.getEnginePower());
        dto.setTorque(car.getTorque());
        if (car.getOwner() != null) {
            dto.setOwnerUsername(car.getOwner().getUsername());
        }
        return dto;
    }
}
