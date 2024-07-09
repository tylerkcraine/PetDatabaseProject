package com.revature.DAO;

import com.revature.Model.Pet;

import java.util.List;

public interface PetDAO {
    public Pet insertPet(Pet pet);
    public List<Pet> findPetsByUserId(int UserId);

}
