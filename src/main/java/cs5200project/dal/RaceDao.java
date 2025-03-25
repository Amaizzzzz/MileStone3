package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Race;

public class RaceDao {
	private RaceDao() {
	}

	public static int create(Connection cxn, String raceName) throws SQLException {
		final String insertRace = """
			INSERT INTO Race(raceName)
			VALUES (?);
		""";

		try (PreparedStatement insertStmt = cxn.prepareStatement(insertRace,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStmt.setString(1, raceName);
			insertStmt.executeUpdate();

			return Utils.getAutoIncrementKey(insertStmt);
		}
	}

	public static Race getRaceById(Connection cxn, int raceId) throws SQLException {
		final String selectRace = "SELECT * FROM Race WHERE raceID = ?;";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectRace)) {
			selectStmt.setInt(1, raceId);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new Race(
						results.getInt("raceID"),
						results.getString("raceName")
					);
				} else {
					return null;
				}
			}
		}
	}

	public static List<Race> getRacesByPartialName(Connection cxn, String partialName) throws SQLException {
		final String selectRace = "SELECT * FROM Race WHERE raceName LIKE ?;";
		List<Race> raceList = new ArrayList<>();
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectRace)) {
			selectStmt.setString(1, "%" + partialName + "%");
			try (ResultSet results = selectStmt.executeQuery()) {
				while (results.next()) {
					raceList.add(new Race(
						results.getInt("raceID"),
						results.getString("raceName")
					));
				}
				return raceList;
			}
		}
	}

	public static <T extends Race> T updateRaceName(Connection cxn, T race, String newRaceName) throws SQLException {
		final String update = "UPDATE Race SET raceName = ? WHERE raceID = ?;";
		try (PreparedStatement updateStmt = cxn.prepareStatement(update)) {
			updateStmt.setString(1, newRaceName);
			updateStmt.setInt(2, race.getRaceID());
			updateStmt.executeUpdate();

			race.setRaceName(newRaceName);
			return race;
		}
	}

	public static void delete(Connection cxn, Race race) throws SQLException {
		final String delete = "DELETE FROM Race WHERE raceID = ?;";
		try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, race.getRaceID());
			stmt.executeUpdate();
		}
	}
}
