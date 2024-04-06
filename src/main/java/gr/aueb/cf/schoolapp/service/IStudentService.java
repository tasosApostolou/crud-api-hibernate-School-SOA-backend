package gr.aueb.cf.schoolapp.service;


import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStudentService {
    Student insertStudent(StudentInsertDTO teacherDTO) throws Exception;
    Student updateStudent(StudentUpdateDTO teacherDTO) throws EntityNotFoundException;
    void deleteStudent(Long id) throws EntityNotFoundException;
    List<Student> getStudentsByLastname(String lastname) throws EntityNotFoundException;
    Student getStudentById(Long id) throws EntityNotFoundException;

}
