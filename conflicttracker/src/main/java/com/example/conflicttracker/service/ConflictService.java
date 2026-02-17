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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    // ==================== MÈTODES PER A L'API REST (AMB DTOs) ====================

    public List<ConflictResponseDTO> findAllConflicts() {
        return conflictRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConflictResponseDTO> findConflictsByStatus(ConflictStatus status) {
        return conflictRepository.findByStatus(status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ConflictResponseDTO findConflictById(Long id) {
        Conflict conflict = conflictRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + id));
        return convertToResponseDTO(conflict);
    }

    public ConflictResponseDTO createConflict(ConflictDTO conflictDTO) {
        Conflict conflict = new Conflict();
        conflict.setName(conflictDTO.getName());
        conflict.setStartDate(conflictDTO.getStartDate());
        conflict.setEndDate(conflictDTO.getEndDate());
        conflict.setStatus(conflictDTO.getStatus());
        conflict.setDescription(conflictDTO.getDescription());
        conflict.setLocation(conflictDTO.getLocation());

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

    public ConflictResponseDTO updateConflict(Long id, ConflictDTO conflictDTO) {
        Conflict existingConflict = conflictRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + id));

        existingConflict.setName(conflictDTO.getName());
        existingConflict.setStartDate(conflictDTO.getStartDate());
        existingConflict.setEndDate(conflictDTO.getEndDate());
        existingConflict.setStatus(conflictDTO.getStatus());
        existingConflict.setDescription(conflictDTO.getDescription());
        existingConflict.setLocation(conflictDTO.getLocation());

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

    public void deleteConflict(Long id) {
        if (!conflictRepository.existsById(id)) {
            throw new RuntimeException("No s'ha trobat el conflicte amb ID: " + id);
        }
        conflictRepository.deleteById(id);
    }

    public List<ConflictResponseDTO> findConflictsByCountryCode(String countryCode) {
        return conflictRepository.findConflictsByCountryCode(countryCode).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==================== MÈTODES PER AL CONTROLADOR THYMELEAF ====================

    public List<Conflict> findAll() {
        return conflictRepository.findAll();
    }

    public long count() {
        return conflictRepository.count();
    }

    public long countActive() {
        return conflictRepository.countByStatus(ConflictStatus.ACTIVE);
    }

    public Optional<Conflict> findById(Long id) {
        return conflictRepository.findById(id);
    }

    public Conflict save(Conflict conflict) {
        return conflictRepository.save(conflict);
    }

    public void deleteById(Long id) {
        conflictRepository.deleteById(id);
    }

    public List<Conflict> findByStatus(ConflictStatus status) {
        return conflictRepository.findByStatus(status);
    }

    public List<Conflict> findByCountryCode(String countryCode) {
        return conflictRepository.findConflictsByCountryCode(countryCode);
    }

    public List<Conflict> findByNameContaining(String name) {
        return conflictRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Conflict> findByDateRange(LocalDate start, LocalDate end) {
        return conflictRepository.findByStartDateBetween(start, end);
    }

    public ConflictStatistics getStatistics() {
        return new ConflictStatistics(
                conflictRepository.count(),
                conflictRepository.countByStatus(ConflictStatus.ACTIVE),
                conflictRepository.countByStatus(ConflictStatus.INACTIVE),
                conflictRepository.countByStatus(ConflictStatus.FROZEN),
                conflictRepository.countByStatus(ConflictStatus.RESOLVED)
        );
    }

    // ==================== MÈTODES AUXILIARS ====================

    private ConflictResponseDTO convertToResponseDTO(Conflict conflict) {
        ConflictResponseDTO dto = new ConflictResponseDTO();
        dto.setId(conflict.getId());
        dto.setName(conflict.getName());
        dto.setStartDate(conflict.getStartDate());
        dto.setEndDate(conflict.getEndDate());
        dto.setStatus(conflict.getStatus());
        dto.setDescription(conflict.getDescription());
        dto.setLocation(conflict.getLocation());

        if (conflict.getCountries() != null) {
            List<CountrySummaryDTO> countryDTOs = conflict.getCountries().stream()
                    .map(countryService::convertToSummaryDTO)
                    .collect(Collectors.toList());
            dto.setCountries(countryDTOs);
        }

        return dto;
    }

    public ConflictResponseDTO convertToDTO(Conflict conflict) {
        return convertToResponseDTO(conflict);
    }

    // ==================== CLASSE INTERNA PER ESTADÍSTIQUES ====================

    public static class ConflictStatistics {
        private final long total;
        private final long active;
        private final long inactive;
        private final long frozen;
        private final long resolved;

        public ConflictStatistics(long total, long active, long inactive, long frozen, long resolved) {
            this.total = total;
            this.active = active;
            this.inactive = inactive;
            this.frozen = frozen;
            this.resolved = resolved;
        }

        public long getTotal() { return total; }
        public long getActive() { return active; }
        public long getInactive() { return inactive; }
        public long getFrozen() { return frozen; }
        public long getResolved() { return resolved; }

        public long getActivePercentage() {
            return total > 0 ? (active * 100 / total) : 0;
        }
    }
}