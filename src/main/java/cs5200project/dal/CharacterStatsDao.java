package cs5200project.dal;

import cs5200project.model.CharacterStats;
import cs5200project.model.Character;
import cs5200project.model.Statistic;
import java.sql.*;
import java.util.*;

public class CharacterStatsDao {
    // Dao classes should not be instantiated.
    // Pass Connection object as parameter in each method
    // Each method should be static
    private CharacterStatsDao() {
        // Private constructor to prevent instantiation
    }

    public static CharacterStats create(Connection connection, Character character, Statistic stat, int charValue) throws SQLException {
        String insertQuery = "INSERT INTO `CharacterStats` (characterID, statID, value) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, character.getCharacterID());
            statement.setInt(2, stat.getStatisticID());
            statement.setInt(3, charValue);
            
            statement.executeUpdate();
        }
        return new CharacterStats(character.getCharacterID(), stat.getStatisticID(), charValue);
    }

    public static List<CharacterStats> getStatsByCharacterId(Connection cxn, int characterID) throws SQLException {
        List<CharacterStats> stats = new ArrayList<>();
        String query = "SELECT * FROM `CharacterStats` WHERE characterID = ?";
        try (PreparedStatement stmt = cxn.prepareStatement(query)) {
            stmt.setInt(1, characterID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stats.add(new CharacterStats(
                        rs.getInt("characterID"),
                        rs.getInt("statID"),
                        rs.getInt("value")
                    ));
                }
            }
        }
        return stats;
    }
}
