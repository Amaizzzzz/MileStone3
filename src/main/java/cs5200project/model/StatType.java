package cs5200project.model;

public class StatType {
    private int statTypeID;      // Primary key (AUTO_INCREMENT)
    private String name;         // Name of the stat type (e.g., "HP", "MP", "Strength")
    private String description;  // Description or additional info

    // Full-args constructor
    public StatType(int statTypeID, String name, String description) {
        this.statTypeID = statTypeID;
        this.name = name;
        this.description = description;
    }

    // No-args constructor
    public StatType() {}

    // Getters and Setters
    public int getStatTypeID() {
        return statTypeID;
    }

    public void setStatTypeID(int statTypeID) {
        this.statTypeID = statTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
