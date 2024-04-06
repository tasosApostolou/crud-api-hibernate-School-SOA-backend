package gr.aueb.cf.schoolapp.service;


import gr.aueb.cf.schoolapp.dto.usersDTO.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserUpdateDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IUserService {
    User insertUser(UserInsertDTO userDTO) throws Exception;
    User updateUser(UserUpdateDTO userDTO) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;
    List<User> getUsersByUsername(String username) throws EntityNotFoundException;
    User getUserById(Long id) throws EntityNotFoundException;

}
