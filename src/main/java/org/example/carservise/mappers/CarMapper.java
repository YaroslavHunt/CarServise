package org.example.carservise.mappers;

import org.example.carservise.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setEnginePower(carDTO.getEnginePower());
        car.setTorque(carDTO.getTorque());
        return car;
    }

    public CarDTO mapToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setModel(car.getModel());
        dto.setEnginePower(car.getEnginePower());
        dto.setTorque(car.getTorque());
        return dto;
    }
}
