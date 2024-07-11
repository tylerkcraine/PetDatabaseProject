package com.revature.DAO;

import com.revature.Exception.UpdateException;
import com.revature.Model.User;
import com.revature.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    Logger l = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> findUserById(int id) {
        try (Connection c = Util.getConnection()) {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt("id");

                return Optional.of(new User(id, user.getFirstName(), user.getLastName(), user.getEmail()));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> insertUser(User user) {
        try (Connection c = Util.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, email) VALUES (?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt("id");
                return Optional.of(new User(id, user.getFirstName(), user.getLastName(), user.getEmail()));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser() {
        return null;
    }
}
