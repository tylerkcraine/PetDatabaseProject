package com.revature.DAO;

import com.revature.Model.User;
import com.revature.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    Logger l = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<User> findAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection c = Util.getConnection()) {
            String sql = "SELECT * FROM users;";
            PreparedStatement ps = c.prepareStatement(sql);

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String newEmail = rs.getString("email");
                users.add(new User(id, firstName, lastName, newEmail));
            }
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.findUserById()");
            l.error(e.getMessage());
            return new ArrayList<>();
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try (Connection c = Util.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                int newId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String newEmail = rs.getString("email");
                return Optional.of(new User(newId, firstName, lastName, newEmail));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.findUserById()");
            l.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserById(int id) {
        try (Connection c = Util.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                int newId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                return Optional.of(new User(newId, firstName, lastName, email));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.findUserById()");
            l.error(e.getMessage());
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
            l.error("SQL Error in UserDAOImpl.insertUser()");
            l.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> updateUser(User user) {
        try (Connection c = Util.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getID());

            int updated = ps.executeUpdate();
            if (updated == 1) {
                return Optional.of(new User(user.getID(), user.getFirstName(), user.getLastName(), user.getEmail()));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.updateUser()");
            l.error(e.getMessage());
            return Optional.empty();
        }
    }
}
