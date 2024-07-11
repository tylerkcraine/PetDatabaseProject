package com.revature.Service;

import com.revature.DAO.UserDAOImpl;
import com.revature.Model.User;
import com.revature.Util;

import java.util.Optional;

public class UserService {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    public Optional<User> createUser(User user) throws IllegalArgumentException{
        if (user.getEmail().isBlank() |
                user.getFirstName().isBlank() |
                user.getLastName().isBlank()) {
            return Optional.empty();
        }
        return userDAO.insertUser(user);
    }
}
