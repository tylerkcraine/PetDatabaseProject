package com.revature;

import com.revature.Controller.PetDatabaseController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        PetDatabaseController controller = new PetDatabaseController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}