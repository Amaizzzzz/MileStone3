package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Clan;

public class ClanDao {
	private ClanDao() {
	}

	public static int create(Connection cxn, String clanName, int raceID) throws SQLException {
		final String insertClan = """
			INSERT INTO Clan(clanName, raceID)
			VALUES (?, ?);
		""";

		try (PreparedStatement insertStmt = cxn.prepareStatement(insertClan,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStmt.setString(1, clanName);
			insertStmt.setInt(2, raceID);
			insertStmt.executeUpdate();

			return Utils.getAutoIncrementKey(insertStmt);
		}
	}

	public static Clan getClanById(Connection cxn, int clanId) throws SQLException {
		final String selectClan = "SELECT * FROM Clan WHERE clanID = ?;";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectClan)) {
			selectStmt.setInt(1, clanId);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new Clan(
						results.getInt("clanID"),
						results.getString("clanName"),
						results.getInt("raceID")
					);
				} else {
					return null;
				}
			}
		}
	}

	public static List<Clan> getClansByPartialName(Connection cxn, String partialName) throws SQLException {
		final String selectClan = "SELECT * FROM Clan WHERE clanName LIKE ?;";
		List<Clan> clanList = new ArrayList<>();
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectClan)) {
			selectStmt.setString(1, "%" + partialName + "%");
			try (ResultSet results = selectStmt.executeQuery()) {
				while (results.next()) {
					clanList.add(new Clan(
						results.getInt("clanID"),
						results.getString("clanName"),
						results.getInt("raceID")
					));
				}
				return clanList;
			}
		}
	}

	public static <T extends Clan> T updateClanName(Connection cxn, T clan, String newClanName) throws SQLException {
		final String update = "UPDATE Clan SET clanName = ? WHERE clanID = ?;";
		try (PreparedStatement updateStmt = cxn.prepareStatement(update)) {
			updateStmt.setString(1, newClanName);
			updateStmt.setInt(2, clan.getClanID());
			updateStmt.executeUpdate();

			clan.setClanName(newClanName);
			return clan;
		}
	}

	public static void delete(Connection cxn, Clan clan) throws SQLException {
		final String delete = "DELETE FROM Clan WHERE clanID = ?;";
		try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, clan.getClanID());
			stmt.executeUpdate();
		}
	}
}