package cs5200project.model;

import java.util.Objects;

public class LevelThreshold {
    private int charLevel;         // Primary key
    private int requiredXP;

    // Default constructor
    public LevelThreshold() {}

    // constructor
    public LevelThreshold(int charLevel, int requiredXP) {
        this.charLevel = charLevel;
        this.requiredXP = requiredXP;
    }

    // Getter of charLevel
    public int getCharLevel() {
        return charLevel;
    }

    // Setter of charLevel
    public void setCharLevel(int charLevel) {   
        this.charLevel = charLevel;
    }

    // Getter of requiredXP
    public int getRequiredXP() {
        return requiredXP;  
    }

    // Setter of requiredXP
    public void setRequiredXP(int requiredXP) {
        this.requiredXP = requiredXP;
    }   

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(charLevel, requiredXP);
    }   

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;      
        LevelThreshold other = (LevelThreshold) obj;
        return charLevel == other.charLevel && requiredXP == other.requiredXP;
    }

    // toString method
    @Override
    public String toString() {
        return String.format("LevelThreshold(level=%d, requiredXP=%d)", charLevel, requiredXP);
    }

}