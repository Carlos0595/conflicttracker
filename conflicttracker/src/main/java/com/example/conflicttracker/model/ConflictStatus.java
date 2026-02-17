package com.example.conflicttracker.model;

public enum ConflictStatus {
    ACTIVE("Actiu"),
    INACTIVE("Inactiu"),    // Afegit
    FROZEN("Congelat"),
    RESOLVED("Resolt");      // Afegit

    private final String displayName;

    ConflictStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}