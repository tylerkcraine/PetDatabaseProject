package com.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Model.User;
import com.revature.Service.PetService;
import com.revature.Service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

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
        return app;
    }

    private void createUserHandler(Context c) throws JsonProcessingException {
        User user = this.mapper.readValue(c.body(), User.class);
        User newUser = this.userService.createUser(user);
        if (newUser == null) {
            c.status(HttpStatus.BAD_REQUEST);
        } else {
            c.json(mapper.writeValueAsString(newUser));
            c.status(HttpStatus.CREATED);
        }
    }
}
