package com.revature.DAO;

import com.revature.Model.User;
import com.revature.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    Logger l = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public User insertUser(User user) {
        String sql = "INSERT INTO pets (first_name, last_name, email) VALUES (?,?,?)";

        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt("id");
                return new User(id, user.getFirstName(), user.getLastName(), user.getEmail());
            }
        } catch (SQLException e) {
            l.debug("insert failed in com.revature.DAO.UserDAOImpl.insertUser()");
            l.error("insert User failed");
        }
        return null;
    }

    @Override
    public User updateUser() {
        return null;
    }
}
