package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.citiesDTO.CityInsertDTO;
import gr.aueb.cf.schoolapp.dto.citiesDTO.CityUpdateDTO;
import gr.aueb.cf.schoolapp.model.Cities;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ICityService {
    Cities insert(CityInsertDTO dto) throws Exception;
    Cities update(CityUpdateDTO dto) throws EntityNotFoundException;
    void delete(Long id) throws EntityNotFoundException;
    Cities getById(Long id) throws EntityNotFoundException;
    List<Cities> getByCity(String city) throws EntityNotFoundException;
}
