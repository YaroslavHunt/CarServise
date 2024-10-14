package org.example.carservise.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.MaintenanceDTO;
import org.example.carservise.entities.Maintenance;
import org.example.carservise.services.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDTO>> getAllMaintenances() {
        List<MaintenanceDTO> maintenances = maintenanceService.getAllMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        MaintenanceDTO maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody @Valid MaintenanceDTO maintenanceDto) {
        MaintenanceDTO maintenance = maintenanceService.createMaintenance(maintenanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance) {
        MaintenanceDTO updatedMaintenance = maintenanceService.updateMaintenance(id, maintenance);
        return ResponseEntity.ok(updatedMaintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
