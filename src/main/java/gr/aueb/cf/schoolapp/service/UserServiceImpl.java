package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.User;
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
public class UserServiceImpl implements IUserService{


    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Inject
    private IUserDAO userDAO;

    @Override
    public User insertUser(UserInsertDTO userDTO) throws Exception {
        User user;
        try {
            JPAHelper.beginTransaction();
            user = Mapper.mapToUser(userDTO);
            user = userDAO.insert(user);
            if (user.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Teacher with id: " + user.getId() + " inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Insert teacher rollback - Error");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    @Override
    public User updateUser(UserUpdateDTO userDTO) throws EntityNotFoundException {
        User userToUpdate;
        User existingUser = null;

        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(userDAO.getById(userDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(User.class, userDTO.getId()));

            userToUpdate = Mapper.mapToUser(userDTO);
            existingUser = userDAO.update(userToUpdate);
            JPAHelper.commitTransaction();
            logger.info("User with id: " + userToUpdate.getId() + " updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Update rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        // return teacherToUpdate;
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(userDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(User.class, id));
            userDAO.delete(id);
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
    public List<User> getUsersByUsername(String username)
            throws EntityNotFoundException {
        List<User> users;
        try {
            JPAHelper.beginTransaction();
            users = userDAO.getByUserName(username);
            if (users.isEmpty()) {
                throw new EntityNotFoundException(List.class, 0L);
            }
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Users not found by lastname");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user;
        try {
            JPAHelper.beginTransaction();
            user = userDAO.getById(id);
            user = Optional.ofNullable(userDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, id));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("User with id: " + id + " not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

}

