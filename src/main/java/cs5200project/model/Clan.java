package cs5200project.model;

import java.util.Objects;

public class Clan {
    private int clanID;         // Primary key
    private String clanName;
    private Race raceID;

    // constructor
    public Clan(int clanID, String clanName, Race raceID) {
        this.clanID = clanID;
        this.clanName = clanName;
        this.raceID = raceID;
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
    public Race getRaceID() {
        return raceID;
        }

    // Setter of raceID
    public void setRaceID(Race raceID) {
        this.raceID = raceID;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(clanID, clanName, raceID);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clan clan = (Clan) obj;
        return clanID == clan.clanID && Objects.equals(clanName, clan.clanName) && Objects.equals(raceID, clan.raceID);
    }

    // toString method
    @Override   
    public String toString() {
        return String.format("Clan[%d, %s, %s]", clanID, clanName, raceID);
    }

}
