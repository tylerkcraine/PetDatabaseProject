package com.revature.DAO;

import com.revature.Model.Pet;

import java.util.List;

public interface PetDAO {
    public List<Pet> findAllPets();
    public List<Pet> findPetsByUserId(int UserId);
    public Pet insertPet(Pet pet);
    public void removePet(Pet pet);
}
