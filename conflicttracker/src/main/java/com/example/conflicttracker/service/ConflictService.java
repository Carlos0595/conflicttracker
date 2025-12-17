package com.example.conflicttracker.service;

import com.example.conflicttracker.dto.ConflictDTO;
import com.example.conflicttracker.dto.ConflictResponseDTO;
import com.example.conflicttracker.dto.CountrySummaryDTO;
import com.example.conflicttracker.model.Conflict;
import com.example.conflicttracker.model.ConflictStatus;
import com.example.conflicttracker.model.Country;
import com.example.conflicttracker.repository.ConflictRepository;
import com.example.conflicttracker.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConflictService {

    @Autowired
    private ConflictRepository conflictRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    // Troba tots els conflictes
    public List<ConflictResponseDTO> findAllConflicts() {
        return conflictRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Troba conflictes per estat
    public List<ConflictResponseDTO> findConflictsByStatus(ConflictStatus status) {
        return conflictRepository.findByStatus(status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Troba un conflicte pel seu ID
    public ConflictResponseDTO findConflictById(Long id) {
        Conflict conflict = conflictRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + id));
        return convertToResponseDTO(conflict);
    }

    // Crea un nou conflicte
    public ConflictResponseDTO createConflict(ConflictDTO conflictDTO) {
        Conflict conflict = new Conflict();
        conflict.setName(conflictDTO.getName());
        conflict.setStartDate(conflictDTO.getStartDate());
        conflict.setStatus(conflictDTO.getStatus());
        conflict.setDescription(conflictDTO.getDescription());

        // Afegim els països involucrats
        if (conflictDTO.getCountryIds() != null) {
            for (Long countryId : conflictDTO.getCountryIds()) {
                Country country = countryRepository.findById(countryId)
                        .orElseThrow(() -> new RuntimeException("No s'ha trobat el país amb ID: " + countryId));
                conflict.addCountry(country);
            }
        }

        Conflict savedConflict = conflictRepository.save(conflict);
        return convertToResponseDTO(savedConflict);
    }

    // Actualitza un conflicte existent
    public ConflictResponseDTO updateConflict(Long id, ConflictDTO conflictDTO) {
        Conflict existingConflict = conflictRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + id));

        existingConflict.setName(conflictDTO.getName());
        existingConflict.setStartDate(conflictDTO.getStartDate());
        existingConflict.setStatus(conflictDTO.getStatus());
        existingConflict.setDescription(conflictDTO.getDescription());

        // Actualitzem els països involucrats
        existingConflict.getCountries().clear();
        if (conflictDTO.getCountryIds() != null) {
            for (Long countryId : conflictDTO.getCountryIds()) {
                Country country = countryRepository.findById(countryId)
                        .orElseThrow(() -> new RuntimeException("No s'ha trobat el país amb ID: " + countryId));
                existingConflict.addCountry(country);
            }
        }

        Conflict updatedConflict = conflictRepository.save(existingConflict);
        return convertToResponseDTO(updatedConflict);
    }

    // Elimina un conflicte
    public void deleteConflict(Long id) {
        if (!conflictRepository.existsById(id)) {
            throw new RuntimeException("No s'ha trobat el conflicte amb ID: " + id);
        }
        conflictRepository.deleteById(id);
    }

    // Troba conflictes per codi de país
    public List<ConflictResponseDTO> findConflictsByCountryCode(String countryCode) {
        return conflictRepository.findConflictsByCountryCode(countryCode).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Converteix entitat Conflict a ConflictResponseDTO
    private ConflictResponseDTO convertToResponseDTO(Conflict conflict) {
        ConflictResponseDTO dto = new ConflictResponseDTO();
        dto.setId(conflict.getId());
        dto.setName(conflict.getName());
        dto.setStartDate(conflict.getStartDate());
        dto.setStatus(conflict.getStatus());
        dto.setDescription(conflict.getDescription());

        // Convertim els països a DTOs resumits
        if (conflict.getCountries() != null) {
            List<CountrySummaryDTO> countryDTOs = conflict.getCountries().stream()
                    .map(countryService::convertToSummaryDTO)
                    .collect(Collectors.toList());
            dto.setCountries(countryDTOs);
        }

        // TODO: Afegir les faccions i esdeveniments quan els tinguem

        return dto;
    }
}
