package com.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Exception.OptionalEmptyException;
import com.revature.Exception.RecordAlreadyExistsException;
import com.revature.Exception.RecordNotFoundException;
import com.revature.Exception.ResetException;
import com.revature.Model.Pet;
import com.revature.Model.User;
import com.revature.Service.PetService;
import com.revature.Service.ResetService;
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
    private ResetService resetService;

    public Javalin startAPI() {
        this.mapper = new ObjectMapper();
        this.userService = new UserService();
        this.petService = new PetService();
        this.resetService = new ResetService();

        Javalin app = Javalin.create();
        app.post("/user", this::createUserHandler);
        app.put("/user", this::updateUserHandler);
        app.get("/user/byEmail/{email}", this::getUserByEmailHandler);
        app.get("/users", this::getUsersHandler);

        app.post("/pet", this::createPetHandler);
        app.get("/pets", this::getPetsHandler);
        app.get("/pet/byUserId", this::getPetsByUserIdHandler);
        app.delete("/pet", this::removePetHandler);
        app.delete("/database", this::resetDatabase);
        return app;
    }

    private void createUserHandler(Context c) {
        try {
            User user = this.mapper.readValue(c.body(), User.class);
            User newUser = this.userService.createUser(user);
            c.json(mapper.writeValueAsString(newUser));
            c.status(HttpStatus.CREATED);
        }
        catch (JsonProcessingException e) {
            c.result("User JSON not formatted correctly");
            c.status(HttpStatus.BAD_REQUEST);
        }
        catch (RecordAlreadyExistsException | IllegalArgumentException e) {
            c.result(e.getMessage());
            c.status(HttpStatus.BAD_REQUEST);
        }
        catch (OptionalEmptyException e) {
            c.result("Unknown error creating user");
            c.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateUserHandler(Context c) {
        try {
            User user = this.mapper.readValue(c.body(), User.class);
            this.userService.updateUser(user);
            c.status(HttpStatus.NO_CONTENT);
        }
        catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
            c.result("User JSON not formatted correctly");
        }
        catch (RecordNotFoundException e) {
            c.status(HttpStatus.NOT_FOUND);
            c.result(e.getMessage());
        }
        catch (OptionalEmptyException e) {
            c.result("Unknown error creating user");
            c.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void getUserByEmailHandler(Context c) {
        try {
            String email = c.pathParam("email");
            User newUser = this.userService.findUserByEmail(email);
            c.status(HttpStatus.OK);
            c.json(mapper.writeValueAsString(newUser));
        }
        catch (JsonProcessingException e) {
            c.status(HttpStatus.BAD_REQUEST);
            c.result("no email in request");
        }
        catch (RecordNotFoundException e) {
            c.status(HttpStatus.NOT_FOUND);
            c.result(e.getMessage());
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

    private void  resetDatabase(Context c) {
        try {
            resetService.resetDatabase();
        } catch (ResetException e) {
            c.status(HttpStatus.INTERNAL_SERVER_ERROR);
            c.result(e.toString());
        }
    }
}
