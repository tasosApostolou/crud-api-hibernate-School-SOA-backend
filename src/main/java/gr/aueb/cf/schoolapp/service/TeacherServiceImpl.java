package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Provider
@ApplicationScoped
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Inject
    private ITeacherDAO teacherDAO;
    @Inject
    private ITeacher2DAO teacher2DAO;

    @Override
    public Teacher insertTeacher(TeacherInsertDTO teacherDTO) throws Exception {
        Teacher teacher;
        try {
            JPAHelper.beginTransaction();
            teacher = Mapper.mapToTeacher(teacherDTO);
            teacher = teacherDAO.insert(teacher);
            if (teacher.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Teacher with id: " + teacher.getId() + " inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Insert teacher rollback - Error");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }

    @Override
    public Teacher updateTeacher(TeacherUpdateDTO teacherDTO) throws EntityNotFoundException {
        Teacher teacherToUpdate;
        Teacher existingTeacher = null;

        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(teacherDAO.getById(teacherDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, teacherDTO.getId()));

            teacherToUpdate = Mapper.mapToTeacher(teacherDTO);
            existingTeacher = teacherDAO.update(teacherToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id: " + teacherToUpdate.getId() + " updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Update rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        // return teacherToUpdate;
        return existingTeacher;
    }

    @Override
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
//            if (teacherDAO.getById(id) == null) {
//                throw new EntityNotFoundException(Teacher.class, id);
//            }
            Optional.ofNullable(teacherDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            teacherDAO.delete(id);
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
    public List<Teacher> getTeachersByLastname(String lastname)
            throws EntityNotFoundException {
        List<Teacher> teachers;
        try {
            JPAHelper.beginTransaction();
            teachers = teacherDAO.getByLastName(lastname);
            if (teachers.isEmpty()) {
                throw new EntityNotFoundException(List.class, 0L);
            }
//            Map<String,Object> criteria = new HashMap<>();
//            criteria.put("lastname",lastname);
//            teachers = Optional.of(teacher2DAO.getByCriteria(Teacher.class, criteria))
//                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Teachers not found by lastname");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(Long id) throws EntityNotFoundException {
        Teacher teacher;
        try {
            JPAHelper.beginTransaction();
            teacher = teacherDAO.getById(id);
            teacher = Optional.ofNullable(teacherDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, id));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Teacher with id: " + id + " not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }

}
