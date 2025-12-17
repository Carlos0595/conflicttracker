package com.example.conflicttracker.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Aquesta classe representa un conflicte bèl·lic a la base de dades
@Entity
@Table(name = "conflicts") // Això crea la taula "conflicts" a la BD
public class Conflict {

    // Identificador únic - clau primària
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom del conflicte - no pot estar buit
    @NotBlank(message = "El nom del conflicte és obligatori")
    @Column(nullable = false)
    private String name;

    // Data d'inici del conflicte - obligatòria
    @NotNull(message = "La data d'inici és obligatòria")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // Estat del conflicte - utilitza el nostre enum
    @Enumerated(EnumType.STRING) // Guarda el text (ACTIVE, FROZEN, ENDED)
    @Column(nullable = false)
    private ConflictStatus status;

    // Descripció del conflicte - text llarg
    @Lob // Per textos grans
    @Column(columnDefinition = "TEXT") // Tipus TEXT a la base de dades
    private String description;

    // Relació MOLTS-A-MOLTS amb països
    // Un conflicte pot estar en múltiples països
    @ManyToMany
    @JoinTable(
            name = "conflict_countries", // Nom de la taula intermitja
            joinColumns = @JoinColumn(name = "conflict_id"), // Clau forana d'aquesta taula
            inverseJoinColumns = @JoinColumn(name = "country_id") // Clau forana de l'altra taula
    )
    private Set<Country> countries = new HashSet<>();

    // Constructor buit - OBLIGATORI per JPA
    public Conflict() {}

    // Constructor amb paràmetres - útil per crear conflictes ràpidament
    public Conflict(String name, LocalDate startDate, ConflictStatus status, String description) {
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
    }

    // MÈTODES PER AFEGIR I ELIMINAR PAÏSOS
    public void addCountry(Country country) {
        this.countries.add(country);
        country.getConflicts().add(this); // Actualitza també l'altre costat de la relació
    }

    public void removeCountry(Country country) {
        this.countries.remove(country);
        country.getConflicts().remove(this);
    }

    // GETTERS I SETTERS - necessaris per accedir a les propietats

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ConflictStatus getStatus() {
        return status;
    }

    public void setStatus(ConflictStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
