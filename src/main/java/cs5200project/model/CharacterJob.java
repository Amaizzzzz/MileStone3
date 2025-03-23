package cs5200project.model;

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

    public int getCharacterID() { return characterID; }
    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public int getJobID() { return jobID; }
    public void setJobID(int jobID) { this.jobID = jobID; }

    public boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(boolean unlocked) { isUnlocked = unlocked; }

    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }
}
