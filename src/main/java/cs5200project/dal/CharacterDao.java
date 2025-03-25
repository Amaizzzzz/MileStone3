package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs5200project.model.Character;

public class CharacterDao {
    protected Connection connection;

	// Dao classes should not be instantiated.
	// Pass Connection object as parameter in each method
	// Each method should be static
    public CharacterDao(Connection connection) {
        this.connection = connection;
    }

//	public static Character create (Connection cxn, int playerID, String
//	 firstName, String lastName, int raceID, Date creationTime, boolean
//	 isNewPlayer, int currentJobID) {
//		
//	}

	// Add Connection cxn as parameter
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
