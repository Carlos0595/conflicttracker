package com.example.conflicttracker.service;

import com.example.conflicttracker.dto.FactionDTO;
import com.example.conflicttracker.model.Faction;
import com.example.conflicttracker.model.Conflict;
import com.example.conflicttracker.model.Country;
import com.example.conflicttracker.repository.FactionRepository;
import com.example.conflicttracker.repository.ConflictRepository;
import com.example.conflicttracker.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FactionService {

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private ConflictRepository conflictRepository;

    @Autowired
    private CountryRepository countryRepository;

    // Troba totes les faccions d'un conflicte
    public List<FactionDTO> findFactionsByConflictId(Long conflictId) {
        return factionRepository.findByConflictId(conflictId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Troba una facció pel seu ID
    public FactionDTO findFactionById(Long id) {
        Faction faction = factionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat la facció amb ID: " + id));
        return convertToDTO(faction);
    }

    // Crea una nova facció
    public FactionDTO createFaction(FactionDTO factionDTO) {
        // Verifiquem que el conflicte existeix
        Conflict conflict = conflictRepository.findById(factionDTO.getConflictId())
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + factionDTO.getConflictId()));

        Faction faction = new Faction();
        faction.setName(factionDTO.getName());
        faction.setConflict(conflict);

        // Afegim països supporters
        if (factionDTO.getSupportingCountryIds() != null) {
            for (Long countryId : factionDTO.getSupportingCountryIds()) {
                Country country = countryRepository.findById(countryId)
                        .orElseThrow(() -> new RuntimeException("No s'ha trobat el país amb ID: " + countryId));
                faction.addSupportingCountry(country);
            }
        }

        Faction savedFaction = factionRepository.save(faction);
        return convertToDTO(savedFaction);
    }

    // Actualitza una facció existent
    public FactionDTO updateFaction(Long id, FactionDTO factionDTO) {
        Faction existingFaction = factionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat la facció amb ID: " + id));

        existingFaction.setName(factionDTO.getName());

        // Actualitzem països supporters
        existingFaction.getSupportingCountries().clear();
        if (factionDTO.getSupportingCountryIds() != null) {
            for (Long countryId : factionDTO.getSupportingCountryIds()) {
                Country country = countryRepository.findById(countryId)
                        .orElseThrow(() -> new RuntimeException("No s'ha trobat el país amb ID: " + countryId));
                existingFaction.addSupportingCountry(country);
            }
        }

        Faction updatedFaction = factionRepository.save(existingFaction);
        return convertToDTO(updatedFaction);
    }

    // Elimina una facció
    public void deleteFaction(Long id) {
        if (!factionRepository.existsById(id)) {
            throw new RuntimeException("No s'ha trobat la facció amb ID: " + id);
        }
        factionRepository.deleteById(id);
    }

    // Converteix entitat Faction a FactionDTO
    private FactionDTO convertToDTO(Faction faction) {
        FactionDTO dto = new FactionDTO();
        dto.setId(faction.getId());
        dto.setName(faction.getName());
        dto.setConflictId(faction.getConflict().getId());

        // Afegim els IDs dels països supporters
        if (faction.getSupportingCountries() != null) {
            List<Long> countryIds = faction.getSupportingCountries().stream()
                    .map(Country::getId)
                    .collect(Collectors.toList());
            dto.setSupportingCountryIds(countryIds);
        }

        return dto;
    }
}
