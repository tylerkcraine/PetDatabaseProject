package com.revature.Service;

import com.revature.DAO.UserDAOImpl;
import com.revature.Model.User;
import com.revature.Util;

public class UserService {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    public User createAccount(User user) throws IllegalArgumentException{
        if (user.getEmail().isBlank() |
                user.getFirstName().isBlank() |
                user.getLastName().isBlank()) {
            throw new IllegalArgumentException();
        }

        User u = userDAO.insertUser(user);
        if (u == null) {
            
        }
        return null;
    }
}
