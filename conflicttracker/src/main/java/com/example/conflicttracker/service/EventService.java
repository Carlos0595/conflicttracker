package com.example.conflicttracker.service;

import com.example.conflicttracker.dto.EventDTO;
import com.example.conflicttracker.model.Event;
import com.example.conflicttracker.model.Conflict;
import com.example.conflicttracker.repository.EventRepository;
import com.example.conflicttracker.repository.ConflictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ConflictRepository conflictRepository;

    // Troba tots els esdeveniments d'un conflicte
    public List<EventDTO> findEventsByConflictId(Long conflictId) {
        return eventRepository.findByConflictId(conflictId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Troba un esdeveniment pel seu ID
    public EventDTO findEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat l'esdeveniment amb ID: " + id));
        return convertToDTO(event);
    }

    // Crea un nou esdeveniment
    public EventDTO createEvent(EventDTO eventDTO) {
        // Verifiquem que el conflicte existeix
        Conflict conflict = conflictRepository.findById(eventDTO.getConflictId())
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el conflicte amb ID: " + eventDTO.getConflictId()));

        Event event = new Event();
        event.setEventDate(eventDTO.getEventDate());
        event.setLocation(eventDTO.getLocation());
        event.setDescription(eventDTO.getDescription());
        event.setConflict(conflict);

        Event savedEvent = eventRepository.save(event);
        return convertToDTO(savedEvent);
    }

    // Actualitza un esdeveniment existent
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat l'esdeveniment amb ID: " + id));

        existingEvent.setEventDate(eventDTO.getEventDate());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setDescription(eventDTO.getDescription());

        Event updatedEvent = eventRepository.save(existingEvent);
        return convertToDTO(updatedEvent);
    }

    // Elimina un esdeveniment
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("No s'ha trobat l'esdeveniment amb ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    // Converteix entitat Event a EventDTO
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setEventDate(event.getEventDate());
        dto.setLocation(event.getLocation());
        dto.setDescription(event.getDescription());
        dto.setConflictId(event.getConflict().getId());
        return dto;
    }
}
