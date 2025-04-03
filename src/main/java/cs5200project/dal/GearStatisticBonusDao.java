package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.GearStatisticBonus;
import cs5200project.model.Gear;
import cs5200project.model.Statistic;

public class GearStatisticBonusDao {
	private GearStatisticBonusDao() {
	}

	public static GearStatisticBonus create(Connection cxn, Gear gear,
			Statistic stats, int bonusValue) throws SQLException {
		String insertBonus = """
				INSERT INTO GearStatisticBonus (itemID, statID, bonusValue)
				VALUES (?, ?, ?);""";
		try (PreparedStatement insertStmt = cxn.prepareStatement(insertBonus)) {
			insertStmt.setInt(1, gear.getItemId());
			insertStmt.setInt(2, stats.getStatisticID());
			insertStmt.setInt(3, bonusValue);
			insertStmt.executeUpdate();
			return new GearStatisticBonus(gear, stats, bonusValue);
		}
	}

	public static GearStatisticBonus getGearStatisticBonusByItemIdAndStatId(
			Connection cxn, Gear gear, Statistic stats) throws SQLException {
		String selectBonus = """
				SELECT * FROM GearStatisticBonus
					WHERE itemID = ? AND statID = ?;
				""";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectBonus)) {
			selectStmt.setInt(1, gear.getItemId());
			selectStmt.setInt(2, stats.getStatisticID());

			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new GearStatisticBonus(gear, stats,
							results.getInt("bonusValue"));
				} else {
					return null;
				}
			}
		}
	}

	public static List<GearStatisticBonus> getGearStatisticBonusByBonusValue(
			Connection cxn,
			int bonusValue) throws SQLException {
		String selectBonus = "SELECT * FROM GearStatisticBonus WHERE bonusValue = ?;";
		List<GearStatisticBonus> bonusList = new ArrayList<>();
		try (PreparedStatement stmt = cxn.prepareStatement(selectBonus)) {
			stmt.setInt(1, bonusValue);
			try (ResultSet results = stmt.executeQuery()) {
				while (results.next()) {
					int itemId = results.getInt("itemID");
					Gear gear = GearDao.getGearByItemID(cxn, itemId);
					int statId = results.getInt("statID");
					Statistic stats = StatisticDao.getStatisticByID(cxn,
							statId);
					bonusList.add(new GearStatisticBonus(
							gear, stats, results.getInt("bonusValue")));
				}
			}
		}
		return bonusList;
	}
}
