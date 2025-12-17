package com.example.conflicttracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// Aquesta classe representa un esdeveniment important d'un conflicte
@Entity
@Table(name = "events") // Taula "events" a la base de dades
public class Event {

    // Identificador únic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Data de l'esdeveniment - obligatòria
    @NotNull(message = "La data de l'esdeveniment és obligatòria")
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    // Ubicació de l'esdeveniment - obligatòria
    @NotBlank(message = "La ubicació és obligatòria")
    @Column(nullable = false)
    private String location;

    // Descripció de l'esdeveniment - text llarg
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    // Relació MOLTS-A-UN amb Conflict
    // Cada esdeveniment pertany a un únic conflicte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conflict_id", nullable = false)
    private Conflict conflict;

    // Constructor buit - necessari per JPA
    public Event() {}

    // Constructor amb paràmetres
    public Event(LocalDate eventDate, String location, String description, Conflict conflict) {
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.conflict = conflict;
    }

    // GETTERS I SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Conflict getConflict() {
        return conflict;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }
}
