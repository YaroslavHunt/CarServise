package org.example.carservise.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carservise.dto.MaintenanceDTO;
import org.example.carservise.entities.Maintenance;
import org.example.carservise.entities.Owner;
import org.example.carservise.job.EmailService;
import org.example.carservise.mappers.MaintenanceMapper;
import org.example.carservise.repositories.MaintenanceRepository;
import org.example.carservise.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final EmailService emailService;
    private final OwnerRepository ownerRepository;


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
        notifyOwnersAboutNewMaintenance(savedMaintenance);
        return maintenanceMapper.mapToDto(savedMaintenance);
    }
    private void notifyOwnersAboutNewMaintenance(Maintenance maintenance) {
        String subject = "New service: " + maintenance.getName();
        String text = String.format("""
            <h1>A new service has been added</h1>
            <p><strong>Name:</strong> %s</p>
            <p><strong>Description:</strong> %s</p>
            <p><strong>Price:</strong> %s $</p>
        """, maintenance.getName(), maintenance.getDescription(), maintenance.getPrice());

        List<Owner> owners = ownerRepository.findAll();
        owners.forEach(owner -> {
            log.info("Sending email to: {}", owner.getEmail());
            emailService.sendEmail(owner.getEmail(), subject, text);
        });
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
