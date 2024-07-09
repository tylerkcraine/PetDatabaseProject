package com.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Model.User;
import com.revature.Service.PetService;
import com.revature.Service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class PetDatabaseController {
    private ObjectMapper mapper;
    private UserService userService;
    private PetService gameService;

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // TODO: Add endpoints
        return app;
    }

    private void createUsers(Context c) throws JsonProcessingException {
        User user = this.mapper.readValue(c.body(), User.class);
        User newUser = this.userService.createAccount(user);
    }
}
