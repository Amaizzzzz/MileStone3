package cs5200project.model;

public class Statistic {
    private int statisticID;  // Primary key (AUTO_INCREMENT)
    private int statValue;    // The stat value (e.g., HP amount, MP amount, etc.)
    private int statTypeID;   // Foreign key referencing StatType


    // Full-args constructor
    public Statistic(int statisticID, int statValue, int statTypeID) {
        this.statisticID = statisticID;
        this.statValue = statValue;
        this.statTypeID = statTypeID;
    }

    // No-args constructor
    public Statistic() {}

    // Getters and Setters
    public int getStatisticID() {
        return statisticID;
    }

    public void setStatisticID(int statisticID) {
        this.statisticID = statisticID;
    }

    public int getStatValue() {
        return statValue;
    }

    public void setStatValue(int statValue) {
        this.statValue = statValue;
    }

    public int getStatTypeID() {
        return statTypeID;
    }

    public void setStatTypeID(int statTypeID) {
        this.statTypeID = statTypeID;
    }
}
