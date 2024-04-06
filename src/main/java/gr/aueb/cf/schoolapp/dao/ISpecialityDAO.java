package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Set;



public interface ISpecialityDAO {
    Speciality insert(Speciality speciality);
    Speciality update(Speciality speciality);
    void delete(Long id);

//    List<Teacher> getTeachersBySpeciality(String speciality);

    List<Speciality> getBySpeciality(String speciality);
    Speciality getById(Long id);
}
