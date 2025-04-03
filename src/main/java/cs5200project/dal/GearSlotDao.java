package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.GearSlot;

public class GearSlotDao {
	private GearSlotDao() {
	}

	public static GearSlot create(Connection cxn, String slotName)
			throws SQLException {
		final String insertGearSlot = "INSERT INTO GearSlot (slotName) VALUES (?);";
		try (PreparedStatement insertStmt = cxn.prepareStatement(insertGearSlot,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStmt.setString(1, slotName);
			insertStmt.executeUpdate();

			return new GearSlot(Utils.getAutoIncrementKey(insertStmt), slotName);	
		}
	}

	public static GearSlot getGearSlotById(Connection cxn, int slotID)
			throws SQLException {
		final String selectGearSlot = "SELECT * FROM GearSlot WHERE slotID = ?;";
		try (PreparedStatement selectStmt = cxn
				.prepareStatement(selectGearSlot)) {
			selectStmt.setInt(1, slotID);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new GearSlot(slotID, results.getString("slotName"));
				} else {
					return null;
				}
			}
		}
	}

	public static List<GearSlot> getGearSlotsByName(Connection cxn,
			String slotName)
			throws SQLException {
		final String selectGearSlot = "SELECT * FROM GearSlot WHERE slotName = ?;";
		try (PreparedStatement stmt = cxn.prepareStatement(selectGearSlot);
				ResultSet results = stmt.executeQuery()) {
			List<GearSlot> gearSlots = new ArrayList<>();
			while (results.next()) {
				gearSlots.add(new GearSlot(results.getInt("slotID"), slotName));
			}
			return gearSlots;
		}
	}

	public static void delete(Connection cxn, GearSlot gearSlot)
			throws SQLException {
		final String deleteGearSlot = "DELETE FROM GearSlot WHERE slotID = ?;";
		try (PreparedStatement deleteStmt = cxn
				.prepareStatement(deleteGearSlot)) {
			deleteStmt.setInt(1, gearSlot.getSlotID());
			deleteStmt.executeUpdate();
		}
	}
}

