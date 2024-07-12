package com.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Model.Pet;
import com.revature.Model.User;
import com.revature.Service.PetService;
import com.revature.Service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class PetDatabaseController {
    private ObjectMapper mapper;
    private UserService userService;
    private PetService petService;

    public Javalin startAPI() {
        this.mapper = new ObjectMapper();
        this.userService = new UserService();
        this.petService = new PetService();
        Javalin app = Javalin.create();
        app.post("/register", this::createUserHandler);
        app.put("/user", this::updateUserHandler);
        app.get("/user/byEmail", this::getUserByEmailHandler);
        app.get("/users", this::getUsersHandler);

        app.post("/pet", this::createPetHandler);
        app.get("/pets", this::getPetsHandler);
        app.get("/pet/byUserId", this::getPetsByUserIdHandler);
        app.delete("/pet", this::removePetHandler);
        return app;
    }

    private void createUserHandler(Context c) {
        try {
            User user = this.mapper.readValue(c.body(), User.class);
            Optional<User> newUser = this.userService.createUser(user);
            if (newUser.isEmpty()) {
                c.status(HttpStatus.BAD_REQUEST);
            } else {
                c.json(mapper.writeValueAsString(newUser.get()));
                c.status(HttpStatus.CREATED);
            }
        } catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateUserHandler(Context c) {
        try {
            User user = this.mapper.readValue(c.body(), User.class);
            Optional<User> newUser = this.userService.updateUser(user);
            if (newUser.isEmpty()) {
                c.status(HttpStatus.BAD_REQUEST);
            } else {
                c.status(HttpStatus.OK);
            }
        } catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void getUserByEmailHandler(Context c) {
        try {
            String email = this.mapper.readTree(c.body()).get("email").asText();
            Optional<User> newUser = this.userService.findUserByEmail(email);
            if (newUser.isEmpty()) {
                c.status(HttpStatus.BAD_REQUEST);
            } else {
                c.status(HttpStatus.OK);
                c.json(mapper.writeValueAsString(newUser.get()));
            }
        } catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void getUsersHandler(Context c) {
        List<User> users = this.userService.findAllUsers();
        c.json(users);
    }

    private void createPetHandler(Context c) {
        Pet newPet = c.bodyAsClass(Pet.class);
        Optional<Pet> pet = petService.insertPet(newPet);
        if (pet.isEmpty()) {
            c.status(HttpStatus.BAD_REQUEST);
        } else {
            c.json(pet.get());
        }
    }

    private void getPetsHandler(Context c) {
        List<Pet> petList = petService.findALLPets();
        c.json(petList);
    }

    private void getPetsByUserIdHandler(Context c) {
        try {
            int ownerId = mapper.readTree(c.body()).get("ownerID").asInt();
            c.json(petService.findPetsByUserId(ownerId));
        } catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void removePetHandler(Context c) {
        try {
            int petId = mapper.readTree(c.body()).get("petID").asInt();
            petService.removePet(petId);
        } catch (JsonProcessingException e) {
            c.status(HttpStatus.OK);
        }
    }
}
