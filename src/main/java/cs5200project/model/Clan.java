package cs5200project.model;

import java.util.Objects;

public class Clan {
    private int clanID;         // Primary key
    private String clanName;
	private Race race;

    // constructor
    public Clan(int clanID, String clanName, Race race) {
        this.clanID = clanID;
        this.clanName = clanName;
        this.race = race;
    }

    // Getter of clanID
    public int getClanID() {
        return clanID;
    }

    // Setter of clanID
    public void setClanID(int clanID) {
        this.clanID = clanID;
    }   

    // Getter of clanName
    public String getClanName() {
        return clanName;
    }
    
    // Setter of clanName
    public void setClanName(String clanName) {  
        this.clanName = clanName;
    }

    // Getter of raceID
    public Race getRace() {
        return race;
        }

    // Setter of raceID
    public void setRace(Race race) {
        this.race = race;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(clanID, clanName, race);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clan clan = (Clan) obj;
        return clanID == clan.clanID && Objects.equals(clanName, clan.clanName) && Objects.equals(race, clan.race);
    }

    // toString method
    @Override   
    public String toString() {
        return String.format("Clan[%d, %s, %s]", clanID, clanName, race);
    }

}
