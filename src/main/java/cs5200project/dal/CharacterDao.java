package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cs5200project.model.Character;

public class CharacterDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CharacterDao() {
        // Private constructor to prevent instantiation
    }

    public static Character create(Connection cxn, int playerID, String
     firstName, String lastName, int raceID, Date creationTime, boolean
     isNewPlayer, int currentJobID) throws SQLException {
        String query = "INSERT INTO Character (playerID, firstName, lastName, raceID, creationTime, isNewPlayer, currentJobID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cxn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, playerID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setInt(4, raceID);
            stmt.setTimestamp(5, new java.sql.Timestamp(creationTime.getTime()));
            stmt.setBoolean(6, isNewPlayer);
            stmt.setInt(7, currentJobID);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating character failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int characterID = generatedKeys.getInt(1);
                    return new Character(characterID, playerID, firstName, lastName, 
                        raceID, creationTime, isNewPlayer, currentJobID);
                } else {
                    throw new SQLException("Creating character failed, no ID obtained.");
                }
            }
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
