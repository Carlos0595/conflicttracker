package com.example.conflicttracker.controller;

import com.example.conflicttracker.dto.EventDTO;
import com.example.conflicttracker.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // GET /api/v1/events/conflict/{conflictId} - Retorna tots els esdeveniments d'un conflicte
    @GetMapping("/conflict/{conflictId}")
    public ResponseEntity<List<EventDTO>> getEventsByConflictId(@PathVariable Long conflictId) {
        List<EventDTO> events = eventService.findEventsByConflictId(conflictId);
        return ResponseEntity.ok(events);
    }

    // GET /api/v1/events/{id} - Retorna un esdeveniment per ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.findEventById(id);
        return ResponseEntity.ok(event);
    }

    // POST /api/v1/events - Crea un nou esdeveniment
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // PUT /api/v1/events/{id} - Actualitza un esdeveniment existent
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE /api/v1/events/{id} - Elimina un esdeveniment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
