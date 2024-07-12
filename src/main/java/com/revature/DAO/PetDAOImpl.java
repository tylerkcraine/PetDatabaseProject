package com.revature.DAO;

import com.revature.Model.Pet;
import com.revature.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetDAOImpl implements PetDAO {
    Logger l = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Pet> findAllPets() {
        String sql = "SELECT * FROM pets";
        ArrayList<Pet> petList = new ArrayList<>();

        try {
            Connection c = Util.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.executeQuery();
            ResultSet result = ps.getResultSet();
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String species = result.getString("species");
                String breed = result.getString("breed");
                int ownerId = result.getInt("owner_id");
                petList.add(new Pet(id,name,species,breed,ownerId));
            }
        } catch (SQLException e) {
            l.debug("SQL error in com.revature.PetDAOImpl.findAllPets method");
            l.error("Find all pets failed");
            return new ArrayList<>();
        }
        return petList;
    }

    @Override
    public List<Pet> findPetsByUserId(int userId) {
        ArrayList<Pet> petList = new ArrayList<>();
        try (Connection c = Util.getConnection()) {
            String sql = "SELECT * FROM pets WHERE owner_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int newId = rs.getInt("id");
                String name = rs.getString("name");
                String species = rs.getString("species");
                String breed = rs.getString("breed");
                petList.add(new Pet(newId, name, species, breed, userId));
            }
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.findUserById()");
            l.error(e.getMessage());
            return new ArrayList<>();
        }
        return petList;
    }

    @Override
    public Optional<Pet> insertPet(Pet pet) {
        try (Connection c = Util.getConnection()) {
            String sql = "INSERT INTO pets (name, species, breed, owner_id) VALUES (?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getSpecies());
            ps.setString(3, pet.getBreed());
            ps.setInt(4, pet.getOwnerID());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt("id");
                return Optional.of(new Pet(id, pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getOwnerID()));
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
    public void removePet(int petId) {
        try (Connection c = Util.getConnection()) {
            String sql = "DELETE FROM pets WHERE id = ?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, petId);

            ps.executeUpdate();
        } catch (SQLException e) {
            l.error("SQL Error in UserDAOImpl.insertUser()");
            l.error(e.getMessage());
        }
    }
}
