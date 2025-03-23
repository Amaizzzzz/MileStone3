package cs5200project.model;

public class CharacterStats {
    private int characterID;
    private int statID;
    private int charValue;

    public CharacterStats(int characterID, int statID, int charValue) {
        this.characterID = characterID;
        this.statID = statID;
        this.charValue = charValue;
    }

    public int getCharacterID() { return characterID; }
    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public int getStatID() { return statID; }
    public void setStatID(int statID) { this.statID = statID; }

    public int getCharValue() { return charValue; }
    public void setCharValue(int charValue) { this.charValue = charValue; }
}
