package com.devsuperior.bds04.controllers;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<EventDTO>> findAllPaged(Pageable pageable){
        Page<EventDTO> response = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable Long id){
        EventDTO response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }
}
