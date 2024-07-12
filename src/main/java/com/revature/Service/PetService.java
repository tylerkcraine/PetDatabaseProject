package com.revature.Service;

import com.revature.DAO.PetDAOImpl;
import com.revature.Model.Pet;

import java.util.List;
import java.util.Optional;

public class PetService {

    private final PetDAOImpl petDAO = new PetDAOImpl();
    public List<Pet> findALLPets() {
        return petDAO.findAllPets();
    }

    public List<Pet> findPetsByUserId(int userId) {
        return petDAO.findPetsByUserId(userId);
    }

    public Optional<Pet> insertPet(Pet pet) {
        if (pet.getName().isBlank() ||
            pet.getBreed().isBlank() ||
            pet.getSpecies().isBlank()) {
            return Optional.empty();
        }
        return petDAO.insertPet(pet);
    }

    public void removePet(int id) {
        petDAO.removePet(id);
    }
}
