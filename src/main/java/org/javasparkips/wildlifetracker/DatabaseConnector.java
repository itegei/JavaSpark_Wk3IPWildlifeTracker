package org.javasparkips.wildlifetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost/wildlife_tracker";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Glorified30*";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
