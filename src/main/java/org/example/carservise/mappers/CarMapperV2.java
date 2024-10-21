package org.example.carservise.mappers;

import org.example.carservise.api.dto.CarDTO;
import org.example.carservise.entities.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapperV2 {
    Car mapToEntity(CarDTO carDTO);

    CarDTO mapToDTO(Car car);
}
