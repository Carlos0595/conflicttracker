package com.example.conflicttracker.controller;

import com.example.conflicttracker.dto.ConflictDTO;
import com.example.conflicttracker.dto.ConflictResponseDTO;
import com.example.conflicttracker.model.ConflictStatus;
import com.example.conflicttracker.service.ConflictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conflicts")
public class ConflictController {

    @Autowired
    private ConflictService conflictService;

    // GET /api/v1/conflicts - Retorna tots els conflictes (amb filtre opcional per estat)
    @GetMapping
    public ResponseEntity<List<ConflictResponseDTO>> getAllConflicts(
            @RequestParam(required = false) ConflictStatus status) {

        List<ConflictResponseDTO> conflicts;
        if (status != null) {
            conflicts = conflictService.findConflictsByStatus(status);
        } else {
            conflicts = conflictService.findAllConflicts();
        }
        return ResponseEntity.ok(conflicts);
    }

    // GET /api/v1/conflicts/{id} - Retorna un conflicte per ID
    @GetMapping("/{id}")
    public ResponseEntity<ConflictResponseDTO> getConflictById(@PathVariable Long id) {
        ConflictResponseDTO conflict = conflictService.findConflictById(id);
        return ResponseEntity.ok(conflict);
    }

    // POST /api/v1/conflicts - Crea un nou conflicte
    @PostMapping
    public ResponseEntity<ConflictResponseDTO> createConflict(@Valid @RequestBody ConflictDTO conflictDTO) {
        ConflictResponseDTO createdConflict = conflictService.createConflict(conflictDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConflict);
    }

    // PUT /api/v1/conflicts/{id} - Actualitza un conflicte existent
    @PutMapping("/{id}")
    public ResponseEntity<ConflictResponseDTO> updateConflict(
            @PathVariable Long id,
            @Valid @RequestBody ConflictDTO conflictDTO) {
        ConflictResponseDTO updatedConflict = conflictService.updateConflict(id, conflictDTO);
        return ResponseEntity.ok(updatedConflict);
    }

    // DELETE /api/v1/conflicts/{id} - Elimina un conflicte
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConflict(@PathVariable Long id) {
        conflictService.deleteConflict(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/conflicts/countries/{code} - Conflictes d'un país
    @GetMapping("/countries/{code}")
    public ResponseEntity<List<ConflictResponseDTO>> getConflictsByCountryCode(@PathVariable String code) {
        List<ConflictResponseDTO> conflicts = conflictService.findConflictsByCountryCode(code.toUpperCase());
        return ResponseEntity.ok(conflicts);
    }
}