package hu.alkfejl.dao;

import hu.alkfejl.model.Screening;
import hu.alkfejl.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User login(String username, String password);

}
