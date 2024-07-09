package com.revature;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Util {
    private static final String url = System.getenv("POSTGRES_JDBC");
    private static final String username = System.getenv("POSTGRES_USER");
    private static final String password = System.getenv("POSTGRES_PASSWORD");

    private static final PGConnectionPoolDataSource ds = new PGConnectionPoolDataSource();

    private static final Logger l;

    static {
        l = LoggerFactory.getLogger(Util.class);
        try {
            ds.setURL(url);
            ds.setUser(username);
            ds.setPassword(password);
        } catch (NullPointerException e) {
            l.error("Environment variables for JDBC not defined");
        }
    }

    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
