package com.example.conflicttracker.controller;

import com.example.conflicttracker.dto.FactionDTO;
import com.example.conflicttracker.service.FactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factions")
public class FactionController {

    @Autowired
    private FactionService factionService;

    // GET /api/v1/factions/conflict/{conflictId} - Retorna totes les faccions d'un conflicte
    @GetMapping("/conflict/{conflictId}")
    public ResponseEntity<List<FactionDTO>> getFactionsByConflictId(@PathVariable Long conflictId) {
        List<FactionDTO> factions = factionService.findFactionsByConflictId(conflictId);
        return ResponseEntity.ok(factions);
    }

    // GET /api/v1/factions/{id} - Retorna una facció per ID
    @GetMapping("/{id}")
    public ResponseEntity<FactionDTO> getFactionById(@PathVariable Long id) {
        FactionDTO faction = factionService.findFactionById(id);
        return ResponseEntity.ok(faction);
    }

    // POST /api/v1/factions - Crea una nova facció
    @PostMapping
    public ResponseEntity<FactionDTO> createFaction(@Valid @RequestBody FactionDTO factionDTO) {
        FactionDTO createdFaction = factionService.createFaction(factionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaction);
    }

    // PUT /api/v1/factions/{id} - Actualitza una facció existent
    @PutMapping("/{id}")
    public ResponseEntity<FactionDTO> updateFaction(
            @PathVariable Long id,
            @Valid @RequestBody FactionDTO factionDTO) {
        FactionDTO updatedFaction = factionService.updateFaction(id, factionDTO);
        return ResponseEntity.ok(updatedFaction);
    }

    // DELETE /api/v1/factions/{id} - Elimina una facció
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaction(@PathVariable Long id) {
        factionService.deleteFaction(id);
        return ResponseEntity.noContent().build();
    }
}
