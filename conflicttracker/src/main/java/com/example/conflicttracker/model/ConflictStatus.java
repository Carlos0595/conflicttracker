package com.example.conflicttracker.model;

// Aquest enum defineix els possibles estats d'un conflicte
// Els enums són llistes de valors constants
public enum ConflictStatus {

    // Valors de l'enum - són constants
    ACTIVE,    // El conflicte està actiu i en curs
    FROZEN,    // El conflicte està congelat o en pausa
    ENDED      // El conflicte ha finalitzat

    // NO posis coma després de l'últim valor!
    // No cal tancar amb ; si no hi ha mètodes addicionals
}
