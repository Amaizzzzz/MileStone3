
package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Character;
import cs5200project.model.InventorySlot;
import cs5200project.model.Item;

public class InventorySlotDao {
	private InventorySlotDao() {
	}

	public static InventorySlot create(Connection cxn, Character character,
			int slotNumber, Item item, int quantityStacked)
			throws SQLException {
		String insertInventorySlot = """
				INSERT INTO InventorySlot (characterID, slotNumber, itemID, quantityStacked)
				VALUES (?, ?, ?, ?);""";
		try (PreparedStatement insertStmt = cxn
				.prepareStatement(insertInventorySlot)) {
			insertStmt.setInt(1, character.getCharacterID());
			insertStmt.setInt(2, slotNumber);
			insertStmt.setInt(3, item.getItemId());
			insertStmt.setInt(4, quantityStacked);
			insertStmt.executeUpdate();
			return new InventorySlot(character, slotNumber, item,
					quantityStacked);
		}
	}

	public static InventorySlot getInventorySlotByCharacterIdAndSlotNumber(
			Connection cxn, Character character, int slotNumber)
			throws SQLException {
		String selectSlot = """
				SELECT * FROM InventorySlot
					WHERE characterID = ? AND slotNumber = ?;
				""";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectSlot)) {
			selectStmt.setInt(1, character.getCharacterID());
			selectStmt.setInt(2, slotNumber);

			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					int itemId = results.getInt("itemID");
					Item item = ItemDao.getItemById(cxn, itemId);
					return new InventorySlot(character, slotNumber, item,
							results.getInt("quantityStacked"));
				} else {
					return null;
				}
			}
		}
	}

	public static List<InventorySlot> getByCharacterID(Connection cxn,
			Character character) throws SQLException {
		String selectSlot = "SELECT * FROM InventorySlot WHERE characterID = ?;";
		List<InventorySlot> slots = new ArrayList<>();
		try (PreparedStatement stmt = cxn.prepareStatement(selectSlot)) {
			stmt.setInt(1, character.getCharacterID());
			try (ResultSet results = stmt.executeQuery()) {
				while (results.next()) {
					int itemId = results.getInt("itemID");
					Item item = ItemDao.getItemById(cxn, itemId);
					slots.add(new InventorySlot(
							character,
							results.getInt("slotNumber"), 
							item,
							results.getInt("quantityStacked")));
				}
			}
		}
		return slots;
	}
}