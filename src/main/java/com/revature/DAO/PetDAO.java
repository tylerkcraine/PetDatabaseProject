package com.revature.DAO;

import com.revature.Model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetDAO {
    public List<Pet> findAllPets();
    public List<Pet> findPetsByUserId(int UserId);
    public Optional<Pet> insertPet(Pet pet);
    public void removePet(Pet pet);
}
