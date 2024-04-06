package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Student;

import java.util.List;

public interface IStudentDAO {

    Student insert(Student student);
    Student update(Student teacher);
    void delete(Long id);
    List<Student> getByLastName(String lastname);
    Student getById(Long id);
}
