package com.revature;

import com.revature.Controller.PetDatabaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        PetDatabaseController controller = new PetDatabaseController();
        Connection c = Util.getConnection();
        System.out.println("Yeah I'm working read-um and weep");
    }
}