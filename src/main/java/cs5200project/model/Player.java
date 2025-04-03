package cs5200project.model;

import java.sql.Timestamp;

public class Player {
    private int playerID;         // Primary key
    private String username;
    private String email;
    private String serverRegion;
    private Timestamp createdAt; 

    // Full-args constructor including createdAt
    public Player(int playerID, String username, String email, String serverRegion, Timestamp createdAt) {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.serverRegion = serverRegion;
        this.createdAt = createdAt;
    }

    // If you prefer a constructor without createdAt, you can keep it:
    public Player(int playerID, String username, String email, String serverRegion) {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.serverRegion = serverRegion;
        // createdAt can be null or set later in the DAO
    }

    // No-args constructor
    public Player() {}

    // Getters / Setters
    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getServerRegion() {
        return serverRegion;
    }
    public void setServerRegion(String serverRegion) {
        this.serverRegion = serverRegion;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
