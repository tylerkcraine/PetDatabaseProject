package com.revature.Service;

import com.revature.Exception.ResetException;
import com.revature.Util;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ResetService {
    public void resetDatabase() {
        URL u = getClass().getClassLoader().getResource("schema.sql");
        BufferedReader b;
        try {
            assert u != null;
            b = new BufferedReader(new InputStreamReader(u.openStream()));
            Connection c = Util.getConnection();
            Statement s = c.createStatement();
            for (String st : b.lines().toList()) {
                s.addBatch(st);
                System.out.println(st);
            }
            s.executeBatch();
        } catch (SQLException e) {
            throw new ResetException("Syntax error in sql file", e);
        } catch (IOException | AssertionError e) {
            throw new ResetException("SQL file not found", e);
        }
    }
}
