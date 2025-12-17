package com.example.conflicttracker.controller;

import com.example.conflicttracker.dto.CountryDTO;
import com.example.conflicttracker.dto.CountryResponseDTO;
import com.example.conflicttracker.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController indica que aquesta classe és un controller REST
// @RequestMapping defineix el prefix per a totes les URL d'aquest controller
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // GET /api/v1/countries - Retorna tots els països
    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        List<CountryResponseDTO> countries = countryService.findAllCountries();
        return ResponseEntity.ok(countries); // Retorna 200 OK amb la llista de països
    }

    // GET /api/v1/countries/{id} - Retorna un país per ID
    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable Long id) {
        CountryResponseDTO country = countryService.findCountryById(id)
                .orElseThrow(() -> new RuntimeException("País no trobat amb ID: " + id));
        return ResponseEntity.ok(country);
    }

    // GET /api/v1/countries/code/{code} - Retorna un país per codi
    @GetMapping("/code/{code}")
    public ResponseEntity<CountryResponseDTO> getCountryByCode(@PathVariable String code) {
        CountryResponseDTO country = countryService.findCountryByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("País no trobat amb codi: " + code));
        return ResponseEntity.ok(country);
    }

    // POST /api/v1/countries - Crea un nou país
    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        CountryResponseDTO createdCountry = countryService.createCountry(countryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry); // Retorna 201 CREATED
    }

    // PUT /api/v1/countries/{id} - Actualitza un país existent
    @PutMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryDTO countryDTO) {
        CountryResponseDTO updatedCountry = countryService.updateCountry(id, countryDTO);
        return ResponseEntity.ok(updatedCountry);
    }

    // DELETE /api/v1/countries/{id} - Elimina un país
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build(); // Retorna 204 NO CONTENT
    }
}