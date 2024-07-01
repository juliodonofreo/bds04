package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class EventService {
    private final EventRepository repository;
    private final CityRepository cityRepository;

    public EventService(EventRepository repository, CityRepository cityRepository){
        this.repository = repository;
        this.cityRepository = cityRepository;
    }

    public Page<EventDTO> findAllPaged(Pageable pageable){
        Page<Event> cities = repository.findAll(pageable);
        return cities.map(EventDTO::new);
    }

    public EventDTO findById(Long id){
        Event Event = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado."));
        return new EventDTO(Event);
    }

    public EventDTO insert(EventDTO dto){
        Event event = new Event();

        event.setName(dto.getName());
        event.setUrl(dto.getUrl());
        event.setDate(dto.getDate());

        City city = cityRepository.getReferenceById(dto.getCityId());
        event.setCity(city);

        event = repository.save(event);
        return new EventDTO(event);
    }
}
