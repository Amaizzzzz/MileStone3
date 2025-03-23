package cs5200project.dal;

import cs5200project.model.Character;
import java.sql.*;
import java.util.*;

public class CharacterDao {
    protected Connection connection;

    public CharacterDao(Connection connection) {
        this.connection = connection;
    }

    public Character getCharacterById(int id) throws SQLException {
        String query = "SELECT * FROM Character WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
