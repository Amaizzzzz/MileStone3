package cs5200project.dal;

import cs5200project.model.CharacterJob;
import java.sql.*;
import java.util.*;

public class CharacterJobDao {
    protected Connection connection;

    public CharacterJobDao(Connection connection) {
        this.connection = connection;
    }

    public List<CharacterJob> getJobsByCharacterId(int characterID) throws SQLException {
        List<CharacterJob> jobs = new ArrayList<>();
        String query = "SELECT * FROM Character_Job WHERE characterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
