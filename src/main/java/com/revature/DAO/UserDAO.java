package com.revature.DAO;

import com.revature.Model.User;

public interface UserDAO {
    public User insertUser(User user);
    public User findUserByEmail(String email);
    public User findUserById(int id);
}
