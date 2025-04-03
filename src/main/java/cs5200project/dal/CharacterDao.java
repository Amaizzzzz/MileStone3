package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cs5200project.model.Character;
import cs5200project.model.Player;
import cs5200project.model.Race;
import cs5200project.model.Job;

public class CharacterDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CharacterDao() {
        // Private constructor to prevent instantiation
    }

    public static Character create(Connection cxn, Player player, String firstName, String lastName, Race race, Date creationTime, boolean isNewPlayer, Job job) throws SQLException {
        String query = "INSERT INTO Character (playerID, firstName, lastName, raceID, creationTime, isNewPlayer, currentJobID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement stmt = 
                cxn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
            stmt.setInt(1, player.getPlayerID());
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setInt(4, race.getRaceID());
            stmt.setTimestamp(5, new java.sql.Timestamp(creationTime.getTime()));
            stmt.setBoolean(6, isNewPlayer);
            stmt.setInt(7, job.getJobID());
            return new Character(Utils.getAutoIncrementKey(stmt), player.getPlayerID(), firstName, lastName, 
                race.getRaceID(), creationTime, isNewPlayer, job.getJobID()); 
            }
    }
    
    public static Character create(Connection cxn, int playerID, String firstName, String lastName, int raceID, Date creationTime, boolean isNewPlayer, int currentJobID) throws SQLException {
        String query = "INSERT INTO `Character` (playerID, firstName, lastName, raceID, creationTime, isNewPlayer, currentJobID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement stmt = 
                cxn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
            stmt.setInt(1, playerID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setInt(4, raceID);
            stmt.setTimestamp(5, new java.sql.Timestamp(creationTime.getTime()));
            stmt.setBoolean(6, isNewPlayer);
            stmt.setInt(7, currentJobID);
            
            // Execute the statement before trying to get the generated keys
            stmt.executeUpdate();
            
            int generatedKey = Utils.getAutoIncrementKey(stmt);
            return new Character(generatedKey, playerID, firstName, lastName, 
                raceID, creationTime, isNewPlayer, currentJobID); 
            }
    }

    public static Character getCharacterById(Connection cxn, int id) throws SQLException {
        String query = "SELECT * FROM Character WHERE characterID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Character(
                        rs.getInt("characterID"),
                        rs.getInt("playerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("raceID"),
                        rs.getTimestamp("creationTime"),
                        rs.getBoolean("isNewPlayer"),
                        rs.getInt("currentJobID")
                    );
                }
            }
        }
        return null;
    }
}
