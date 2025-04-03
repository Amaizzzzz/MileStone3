package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs5200project.model.Player;
import cs5200project.model.Race;

public class RaceDao {
	private RaceDao() {
	}

	public static Race create(Connection cxn, String raceName) throws SQLException {
		final String insertRace = """
			INSERT INTO Race(raceName)
			VALUES (?);
		""";

		try (PreparedStatement insertStmt = cxn.prepareStatement(insertRace,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStmt.setString(1, raceName);
			insertStmt.executeUpdate();

			return new Race(Utils.getAutoIncrementKey(insertStmt), raceName);
		}
	}

	public static Race getRaceById(Connection cxn, int raceId)
			throws SQLException {
		final String selectRace = """
			SELECT * FROM Race 
			WHERE raceID = ?;
		""";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectRace)) {
			selectStmt.setInt(1, raceId);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					String raceName = results.getString("raceName");
					return new Race(raceId, raceName);
				} else {
					return null;
				}
			}
		}
	}

	public static void delete(Connection cxn, Race race) throws SQLException {
		final String delete = """
			DELETE FROM Race 
			WHERE raceID = ?;
		""";
		try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, race.getRaceID());
			stmt.executeUpdate();
		}
	}
}
