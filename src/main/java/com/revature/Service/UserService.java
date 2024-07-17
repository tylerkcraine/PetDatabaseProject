package com.revature.Service;

import com.revature.DAO.UserDAOImpl;
import com.revature.Exception.OptionalEmptyException;
import com.revature.Exception.RecordAlreadyExistsException;
import com.revature.Exception.RecordNotFoundException;
import com.revature.Model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    public User createUser(User user) throws IllegalArgumentException, RecordAlreadyExistsException, OptionalEmptyException {
        if (user.getEmail().isBlank() || user.getFirstName().isBlank() || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("None of the fields can be blank");
        }
        if (userDAO.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RecordAlreadyExistsException("Email is already registered");
        }

        Optional<User> newUser = userDAO.insertUser(user);
        if (newUser.isEmpty()) {
            throw new OptionalEmptyException("Unknown error occurred");
        }

        return newUser.get();
    }

    public void updateUser(User user) throws OptionalEmptyException, RecordNotFoundException {
        if (user.getID() == 0) {
            throw new RecordNotFoundException("Request must include the user's ID (Field named: \"ID\")");
        }

        if (userDAO.findUserById(user.getID()).isEmpty()) {
            throw new RecordNotFoundException("User not in the database");
        }

        Optional<User> newUser = userDAO.updateUser(user);
        if (newUser.isEmpty()) {
            throw new OptionalEmptyException("Unknown error occurred");
        }
    }

    public User findUserByEmail(String email) throws RecordNotFoundException {
        Optional<User> newUser = userDAO.findUserByEmail(email);
        if (newUser.isEmpty()) {
            throw new RecordNotFoundException("User with that email not found");
        }
        return newUser.get();
    }

    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }
}
