package gr.aueb.cf.schoolapp.dao;


import gr.aueb.cf.schoolapp.model.User;

import java.util.List;

public interface IUserDAO {
    User insert(User user);
    User update(User teacher);
    void delete(Long id);
    List<User> getByUserName(String username);
    User getById(Long id);
}

