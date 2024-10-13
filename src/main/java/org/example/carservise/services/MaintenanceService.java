package org.example.carservise.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.MaintenanceDTO;
import org.example.carservise.entities.Maintenance;
import org.example.carservise.mappers.MaintenanceMapper;
import org.example.carservise.repositories.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;

    public List<MaintenanceDTO> getAllMaintenances() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        return maintenances.stream().map(maintenanceMapper::mapToDto).toList();
    }

    public MaintenanceDTO getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance with id=%s not found".formatted(id)));
        return maintenanceMapper.mapToDto(maintenance);
    }

    public MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO) {
        Maintenance maintenance = maintenanceMapper.mapToEntity(maintenanceDTO);
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
        return maintenanceMapper.mapToDto(savedMaintenance);
    }

    @Transactional
    public MaintenanceDTO updateMaintenance(Long id, Maintenance newMaintenance) {
        return maintenanceRepository.findById(id)
                .map(updatingMaintenance -> {
                    updatingMaintenance.setName(newMaintenance.getName());
                    updatingMaintenance.setPrice(newMaintenance.getPrice());
                    updatingMaintenance.setDescription(newMaintenance.getDescription());

                    return maintenanceMapper.mapToDto(updatingMaintenance);
                })
                .orElseThrow(() -> new IllegalArgumentException("Maintenance with id=%s not found".formatted(id)));
    }

    public void deleteMaintenance(Long id) {
        if (maintenanceRepository.existsById(id)) {
            maintenanceRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Maintenance with id=%s not found".formatted(id));
        }
    }
}
