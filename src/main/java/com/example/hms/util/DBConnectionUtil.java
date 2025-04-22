package com.example.hms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing database connections
 */
public class DBConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(DBConnectionUtil.class.getName());

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/hms_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // Set your MySQL password here

    // Load the JDBC driver during class initialization
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver registered successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to register MySQL JDBC driver", e);
            throw new RuntimeException("Failed to register MySQL JDBC driver", e);
        }
    }

    /**
     * Get a fresh connection to the database
     * @return Connection object
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(true);
            LOGGER.fine("Database connection established successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection: " + e.getMessage(), e);
        }
        return conn;
    }

    /**
     * Close the database connection
     * @param connection The connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    LOGGER.fine("Database connection closed successfully");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to close database connection", e);
            }
        }
    }

    /**
     * Close resources in a finally block
     * @param connection The connection to close
     */
    public static void closeResources(Connection connection) {
        closeConnection(connection);
    }
}
