package cs5200project.model;

import java.util.Objects;
public class Race {
    private int raceID;         // Primary key
    private String raceName;

    // Full-args constructor
    public Race(int raceID, String raceName) {
        this.raceID = raceID;
        this.raceName = raceName;
    }

    // Getter of raceID
    public int getRaceID() {
        return raceID;
    }

    // Setter of raceID
    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    // Getter of raceName
    public String getRaceName() {
        return raceName;
    }

    // Setter of raceName
    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(raceID, raceName);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Race race = (Race) obj;
        return raceID == race.raceID && Objects.equals(raceName, race.raceName);
    }     

    // toString method
    @Override
    public String toString() {
        return String.format("Race[%d, %s]", raceID, raceName);
    }

}
