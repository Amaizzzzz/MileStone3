package cs5200project.dal;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) {
        try {
            // Get the connection manager instance
            ConnectionManager connectionManager = ConnectionManager.getInstance();
            
            // Test the connection
            System.out.println("Testing database connection...");
            Connection connection = connectionManager.getConnection();
            
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");
                System.out.println("Active connections: " + connectionManager.getActiveConnections());
                
                // Test a simple query
                connection.createStatement().execute("SELECT 1");
                System.out.println("Query execution successful!");
                
                // Release the connection back to the pool
                connectionManager.releaseConnection(connection);
                System.out.println("Connection released back to pool");
            } else {
                System.out.println("Failed to establish database connection");
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the database driver and tests the connection
     * @return true if initialization is successful, false otherwise
     */
    public static boolean initializeDriver() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC Driver: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Tests the database connection
     * @return true if connection test is successful, false otherwise
     */
    public static boolean testConnection() {
        ConnectionManager connectionManager = null;
        Connection connection = null;
        
        try {
            connectionManager = ConnectionManager.getInstance();
            connection = connectionManager.getConnection();
            
            if (connection != null && !connection.isClosed()) {
                connection.createStatement().execute("SELECT 1");
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        } finally {
            if (connection != null && connectionManager != null) {
                connectionManager.releaseConnection(connection);
            }
        }
    }
} 