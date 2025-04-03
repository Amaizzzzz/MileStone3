package cs5200project.dal;

import cs5200project.model.CharacterJob;
import cs5200project.model.Character;
import cs5200project.model.Job;
import java.sql.*;
import java.util.*;

public class CharacterJobDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CharacterJobDao() {
        // Private constructor to prevent instantiation
    }

    public static CharacterJob create(Connection connection, Character character, Job job, boolean isUnlocked, int XP) throws SQLException {
        String insertQuery = "INSERT INTO `CharacterJob` (characterID, jobID, isUnlocked, XP) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, character.getCharacterID());
            statement.setInt(2, job.getJobID());
            statement.setBoolean(3, isUnlocked);
            statement.setInt(4, XP);
            statement.executeUpdate();
        }
        
        return new CharacterJob(character.getCharacterID(), job.getJobID(), isUnlocked, XP);
    }

    public static List<CharacterJob> getJobsByCharacterId(Connection cxn, int characterID) throws SQLException {
        List<CharacterJob> jobs = new ArrayList<>();
        String query = "SELECT * FROM Character_Job WHERE characterID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, characterID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    jobs.add(new CharacterJob(
                        rs.getInt("characterID"),
                        rs.getInt("jobID"),
                        rs.getBoolean("isUnlocked"),
                        rs.getInt("XP")
                    ));
                }
            }
        }
        return jobs;
    }
}
