package cs5200project.model;

import java.util.Date;

public class Character {
    private int characterID;
    private int playerID;
    private String firstName;
    private String lastName;
    private int raceID;
    private Date creationTime;
    private boolean isNewPlayer;
    private int currentJobID;

    public Character(int characterID, int playerID, String firstName, String lastName, int raceID, Date creationTime, boolean isNewPlayer, int currentJobID) {
        this.characterID = characterID;
        this.playerID = playerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.raceID = raceID;
        this.creationTime = creationTime;
        this.isNewPlayer = isNewPlayer;
        this.currentJobID = currentJobID;
    }

    public int getCharacterID() { return characterID; }
    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public int getPlayerID() { return playerID; }
    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getRaceID() { return raceID; }
    public void setRaceID(int raceID) { this.raceID = raceID; }

    public Date getCreationTime() { return creationTime; }
    public void setCreationTime(Date creationTime) { this.creationTime = creationTime; }

    public boolean isNewPlayer() { return isNewPlayer; }
    public void setNewPlayer(boolean newPlayer) { isNewPlayer = newPlayer; }

    public int getCurrentJobID() { return currentJobID; }
    public void setCurrentJobID(int currentJobID) { this.currentJobID = currentJobID; }
}
