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

@RestController
@RequestMapping("/api/v1/countries")
@CrossOrigin(origins = "http://localhost:5173")  // ← ESTO ES NUEVO
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        List<CountryResponseDTO> countries = countryService.findAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable Long id) {
        CountryResponseDTO country = countryService.findCountryById(id)
                .orElseThrow(() -> new RuntimeException("País no trobat amb ID: " + id));
        return ResponseEntity.ok(country);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CountryResponseDTO> getCountryByCode(@PathVariable String code) {
        CountryResponseDTO country = countryService.findCountryByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("País no trobat amb codi: " + code));
        return ResponseEntity.ok(country);
    }

    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        CountryResponseDTO createdCountry = countryService.createCountry(countryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryDTO countryDTO) {
        CountryResponseDTO updatedCountry = countryService.updateCountry(id, countryDTO);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}