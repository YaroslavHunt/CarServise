package org.example.carservise.controllers;

import lombok.RequiredArgsConstructor;
import org.example.carservise.dto.OwnerDTO;
import org.example.carservise.services.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable(name = "id") Long ownerId) {
        return ResponseEntity.ok(ownerService.getOwnerById(ownerId));
    }

    @PostMapping
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.ok(ownerService.createOwner(ownerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable(name = "id") Long ownerId, @RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.ok(ownerService.updateOwner(ownerId, ownerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable(name = "id") Long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.noContent().build();
    }
}
