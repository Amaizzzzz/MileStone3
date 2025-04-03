package cs5200project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionManager {
    private static final int MAX_POOL_SIZE = 10;
    private static final int CONNECTION_TIMEOUT = 5; // seconds
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ffxiv_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    
    private static ConnectionManager instance;
    private final BlockingQueue<Connection> connectionPool;
    private int activeConnections = 0;
    
    private ConnectionManager() {
        connectionPool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);
        initializePool();
    }
    
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    private void initializePool() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create initial connections
            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                createConnection();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC Driver", e);
        }
    }
    
    private void createConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("useSSL", "false");
            props.setProperty("serverTimezone", "UTC");
            
            Connection connection = DriverManager.getConnection(DB_URL, props);
            connectionPool.offer(connection);
            activeConnections++;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }
    
    public Connection getConnection() throws SQLException {
        try {
            Connection connection = connectionPool.poll(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            if (connection == null || connection.isClosed()) {
                createConnection();
                connection = connectionPool.poll(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            }
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for connection", e);
        }
    }
    
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (connection.isClosed()) {
                    createConnection();
                } else {
                    connectionPool.offer(connection);
                }
            } catch (SQLException e) {
                // Log the error but don't throw it
                e.printStackTrace();
            }
        }
    }
    
    public void closeAllConnections() {
        while (!connectionPool.isEmpty()) {
            try {
                Connection connection = connectionPool.poll();
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                activeConnections--;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getActiveConnections() {
        return activeConnections;
    }
}
