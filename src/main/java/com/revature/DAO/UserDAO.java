package com.revature.DAO;

import com.revature.Model.User;

import java.util.List;

public interface UserDAO {
    public List<User> findAllUsers();
    public User findUserByEmail(String email);
    public User findUserById(int id);
    public User insertUser(User user);
    public User updateUser();
}
