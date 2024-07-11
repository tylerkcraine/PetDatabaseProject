package com.revature.DAO;

import com.revature.Model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public List<User> findAllUsers();
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(int id);
    public Optional<User> insertUser(User user) throws SQLException;
    public Optional<User> updateUser(User user);
}
