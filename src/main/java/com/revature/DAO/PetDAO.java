package com.revature.DAO;

import com.revature.Model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetDAO {
    List<Pet> findAllPets();
    List<Pet> findPetsByUserId(int UserId);
    Optional<Pet> insertPet(Pet pet);
    void removePet(int petId);
}
