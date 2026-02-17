package com.example.conflicttracker.service;

import com.example.conflicttracker.dto.CountryDTO;
import com.example.conflicttracker.dto.CountryResponseDTO;
import com.example.conflicttracker.dto.CountrySummaryDTO;
import com.example.conflicttracker.model.Country;
import com.example.conflicttracker.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service indica que aquesta classe és un component de servei de Spring
// Gestiona la lògica de negoci relacionada amb els països
@Service
@Transactional // Totes les operacions es fan dins d'una transacció de base de dades
public class CountryService {

    // Injecció de dependències - Spring injecta automàticament el repository
    @Autowired
    private CountryRepository countryRepository;

    // Troba tots els països
    public List<CountryResponseDTO> findAllCountries() {
        // Obtenim tots els països de la base de dades
        List<Country> countries = countryRepository.findAll();

        // Convertim les entitats a DTOs de resposta
        return countries.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Troba un país pel seu ID
    public Optional<CountryResponseDTO> findCountryById(Long id) {
        return countryRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    // Troba un país pel seu codi
    public Optional<CountryResponseDTO> findCountryByCode(String code) {
        return countryRepository.findByCode(code)
                .map(this::convertToResponseDTO);
    }

    // Crea un nou país
    public CountryResponseDTO createCountry(CountryDTO countryDTO) {
        // Verifiquem que no existeixi un país amb el mateix codi
        if (countryRepository.findByCode(countryDTO.getCode()).isPresent()) {
            throw new RuntimeException("Ja existeix un país amb el codi: " + countryDTO.getCode());
        }

        // Creem la nova entitat país
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setCode(countryDTO.getCode().toUpperCase()); // Guardem en majúscules

        // Guardem a la base de dades
        Country savedCountry = countryRepository.save(country);

        // Retornem el DTO de resposta
        return convertToResponseDTO(savedCountry);
    }

    // Actualitza un país existent
    public CountryResponseDTO updateCountry(Long id, CountryDTO countryDTO) {
        // Busquem el país existent
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el país amb ID: " + id));

        // Actualitzem les propietats
        existingCountry.setName(countryDTO.getName());

        // Si canvia el codi, verifiquem que no existeixi un altre país amb el nou codi
        if (!existingCountry.getCode().equals(countryDTO.getCode())) {
            if (countryRepository.findByCode(countryDTO.getCode()).isPresent()) {
                throw new RuntimeException("Ja existeix un altre país amb el codi: " + countryDTO.getCode());
            }
            existingCountry.setCode(countryDTO.getCode().toUpperCase());
        }

        // Guardem els canvis
        Country updatedCountry = countryRepository.save(existingCountry);
        return convertToResponseDTO(updatedCountry);
    }

    // Elimina un país
    public void deleteCountry(Long id) {
        // Verifiquem que el país existeix
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("No s'ha trobat el país amb ID: " + id);
        }

        // TODO: Podríem verificar que el país no estigui involucrat en cap conflicte abans d'eliminar-lo

        countryRepository.deleteById(id);
    }

    // Mètode per convertir una entitat Country a CountryResponseDTO
    private CountryResponseDTO convertToResponseDTO(Country country) {
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setCode(country.getCode());

        // Convertim les relacions a DTOs resumits
        if (country.getConflicts() != null) {
            List<CountrySummaryDTO> conflictDTOs = country.getConflicts().stream()
                    .map(conflict -> new CountrySummaryDTO(conflict.getId(), conflict.getName(), ""))
                    .collect(Collectors.toList());
        }

        return dto;
    }

    // Mètode auxiliar per convertir a DTO resumit
    public CountrySummaryDTO convertToSummaryDTO(Country country) {
        return new CountrySummaryDTO(country.getId(), country.getName(), country.getCode());
    }
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }
}
