package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentUpdateDTO;

import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
@Provider
@ApplicationScoped
public class StudentServiceImpl implements IStudentService{
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Inject
    private IStudentDAO studentDAO;


    @Override
    public Student insertStudent(StudentInsertDTO studentDTO) throws Exception {
        Student student;
        try {
            JPAHelper.beginTransaction();
            student = Mapper.mapToStudent(studentDTO);
            student = studentDAO.insert(student);
            if (student.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Student with id: " + student.getId() + " inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Insert Student rollback - Error");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }

    @Override
    public Student updateStudent(StudentUpdateDTO studentDTO) throws EntityNotFoundException {
        Student studentToUpdate;
        Student existingStudent = null;

        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(studentDAO.getById(studentDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Student.class, studentDTO.getId()));

            studentToUpdate = Mapper.mapToStudent(studentDTO);
            existingStudent = studentDAO.update(studentToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Student with id: " + studentToUpdate.getId() + " updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Update rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        // return teacherToUpdate;
        return existingStudent;
    }

    @Override
    public void deleteStudent(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(studentDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Student.class, id));
            studentDAO.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id: " + id + " deleted");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Delete teacher rollback");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Student> getStudentsByLastname(String lastname)
            throws EntityNotFoundException {
        List<Student> students;
        try {
            JPAHelper.beginTransaction();
            students = studentDAO.getByLastName(lastname);
            if (students.isEmpty()) {
                throw new EntityNotFoundException(List.class, 0L);
            }
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Students not found by lastname");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return students;
    }

    @Override
    public Student getStudentById(Long id) throws EntityNotFoundException {
        Student student;
        try {
            JPAHelper.beginTransaction();
            student = studentDAO.getById(id);
            student = Optional.ofNullable(studentDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, id));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Teacher with id: " + id + " not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }

}
