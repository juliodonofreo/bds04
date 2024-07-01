package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository){
        this.repository = repository;
    }

    public List<CityDTO> findAll(){
        List<City> cities = repository.findAllByOrderByNameAsc();
        return cities.stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    public CityDTO findById(Long id){
        City city = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada."));
        return new CityDTO(city);
    }

    public CityDTO insert(CityDTO dto){
        City city = new City();

        city.setName(dto.getName());
        city = repository.save(city);
        return new CityDTO(city);
    }
}
