package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Clan;
import cs5200project.model.Race;

public class ClanDao {
	private ClanDao() {
	}

	public static Clan create(Connection cxn, String clanName, Race race) throws SQLException {
		final String insertClan = """
			INSERT INTO Clan(clanName, raceID)
			VALUES (?, ?);
		""";

		try (PreparedStatement insertStmt = cxn.prepareStatement(
			  insertClan,
			  Statement.RETURN_GENERATED_KEYS
			)) {
			insertStmt.setString(1, clanName);
			insertStmt.setInt(2, race.getRaceID());
			insertStmt.executeUpdate();

			return new Clan(Utils.getAutoIncrementKey(insertStmt), clanName, race);
		}
	}

	public static Clan getClanById(Connection cxn, Clan clan) throws SQLException {
		final String selectClan = """
			SELECT * FROM Clan 
			  WHERE clanID = ?;
		""";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectClan)) {
			selectStmt.setInt(1, clan.getClanID());
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					Race race = RaceDao.getRaceById(cxn, results.getInt("raceID"));
					return new Clan(
						results.getInt("clanID"),
						results.getString("clanName"),
						race
					);
				} else {
					return null;
				}
			}
		}
	}

	public static List<Clan> getClansByRace(Connection cxn, Race race) throws SQLException {
		final String selectClan = """
			SELECT * FROM Clan 
			WHERE raceID = ?;
		""";
		List<Clan> clanList = new ArrayList<>();
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectClan)) {
			selectStmt.setInt(1, race.getRaceID());
			try (ResultSet results = selectStmt.executeQuery()) {
				while (results.next()) {
					Race race1 = RaceDao.getRaceById(cxn,
							results.getInt("raceID"));
					clanList.add(new Clan(
						results.getInt("clanID"),
						results.getString("clanName"),
							race1
					));
				}
				return clanList;
			}
		}
	}

	public static void delete(Connection cxn, Clan clan) throws SQLException {
		final String delete = """
			DELETE FROM Clan 
			WHERE clanID = ?;
		""";
		try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, clan.getClanID());
			stmt.executeUpdate();
		}
	}
}