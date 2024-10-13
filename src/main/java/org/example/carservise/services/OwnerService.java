package org.example.carservise.services;

import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.OwnerDTO;
import org.example.carservise.entities.Owner;
import org.example.carservise.mappers.OwnerMapper;
import org.example.carservise.repositories.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;


    public List<OwnerDTO> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream()
                .map(ownerMapper::toDTO)
                .toList();
    }

    public OwnerDTO getOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .map(ownerMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }

    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner owner = ownerMapper.toOwner(ownerDTO);
        return ownerMapper.toDTO(ownerRepository.save(owner));
    }

    public OwnerDTO updateOwner(Long ownerId, OwnerDTO ownerDTO) {
        Owner existingOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        existingOwner.setUsername(ownerDTO.getUsername());
        existingOwner.setEmail(ownerDTO.getEmail());
        return ownerMapper.toDTO(ownerRepository.save(existingOwner));
    }

    public void deleteOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        if (!owner.getCars().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot delete owner with id=%s because they have associated cars.".formatted(ownerId));
        }
        ownerRepository.delete(owner);
    }


}
