package cs5200project.dal;

import cs5200project.model.CharacterStats;
import java.sql.*;
import java.util.*;

public class CharacterStatsDao {
    protected Connection connection;

    public CharacterStatsDao(Connection connection) {
        this.connection = connection;
    }

    public List<CharacterStats> getStatsByCharacterId(int characterID) throws SQLException {
        List<CharacterStats> stats = new ArrayList<>();
        String query = "SELECT * FROM Character_Stats WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, characterID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stats.add(new CharacterStats(
                        rs.getInt("characterID"),
                        rs.getInt("statID"),
                        rs.getInt("charValue")
                    ));
                }
            }
        }
        return stats;
    }
}
