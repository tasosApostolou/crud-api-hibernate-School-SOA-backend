package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityInsertDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityUpdateDTO;

import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ISpecialityService {
    Speciality insertSpeciality(SpecialityInsertDTO specialityDTO) throws Exception;

    Speciality updateSpeciality(SpecialityUpdateDTO specialityDTO) throws EntityNotFoundException;
    void deleteSpeciality(Long id) throws EntityNotFoundException;
//    List<Teacher> getTeachersBySpeciality(String Speciality) throws EntityNotFoundException;
    Speciality getSpecialityById(Long id) throws EntityNotFoundException;
}
