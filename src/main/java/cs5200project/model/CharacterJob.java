package cs5200project.model;

import java.util.Objects;

public class CharacterJob {
    private int characterID;
    private int jobID;
    private boolean isUnlocked;
    private int xp;

    public CharacterJob(int characterID, int jobID, boolean isUnlocked, int xp) {
        this.characterID = characterID;
        this.jobID = jobID;
        this.isUnlocked = isUnlocked;
        this.xp = xp;
    }

    // Getters and Setters
    public int getCharacterID() { 
        return characterID; 
    }
    
    public void setCharacterID(int characterID) { 
        this.characterID = characterID; 
    }

    public int getJobID() { 
        return jobID; 
    }
    
    public void setJobID(int jobID) { 
        this.jobID = jobID; 
    }

    public boolean isUnlocked() { 
        return isUnlocked; 
    }
    
    public void setUnlocked(boolean unlocked) { 
        isUnlocked = unlocked; 
    }

    public int getXp() { 
        return xp; 
    }
    
    public void setXp(int xp) { 
        this.xp = xp; 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(characterID, jobID, isUnlocked, xp);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            
        CharacterJob other = (CharacterJob) obj;
        return characterID == other.characterID 
                && jobID == other.jobID
                && isUnlocked == other.isUnlocked
                && xp == other.xp;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CharacterJob(characterID=%d, jobID=%d, isUnlocked=%s, xp=%d)",
                characterID, jobID, isUnlocked, xp);
    }
}
