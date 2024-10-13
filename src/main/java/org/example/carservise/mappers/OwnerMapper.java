package org.example.carservise.mappers;

import org.example.carservise.dto.OwnerDTO;
import org.example.carservise.entities.Owner;
import org.mapstruct.Mapper;

@Mapper
public interface OwnerMapper {
    OwnerDTO toDTO(Owner owner);
    Owner toOwner(OwnerDTO ownerDTO);
}
