package org.example.carservise.mappers;

import org.example.carservise.dto.MaintenanceDTO;
import org.example.carservise.entities.Maintenance;
import org.mapstruct.Mapper;

@Mapper
public interface MaintenanceMapper {
    MaintenanceDTO mapToDto(Maintenance maintenance);
    Maintenance mapToEntity(MaintenanceDTO maintenanceDTO);
}
