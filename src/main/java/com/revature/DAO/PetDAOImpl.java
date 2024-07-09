package com.revature.DAO;

import com.revature.Model.Pet;
import com.revature.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                petList.add(new Pet(id,name,species,breed));
            }
        } catch (SQLException e) {
            l.debug("SQL error in com.revature.PetDAOImpl.findAllPets method");
            l.error("Find all pets failed");
        }
        return petList;
    }

    @Override
    public List<Pet> findPetsByUserId(int UserId) {
        return null;
    }

    @Override
    public Pet insertPet(Pet pet) {
        return null;
    }

    @Override
    public void removePet(Pet pet) {

    }
}
