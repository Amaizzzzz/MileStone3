package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Gear;
import cs5200project.model.Item;

public class GearDao {
	private GearDao() {
	}

	public static Gear create(Connection cxn, 
			String itemName, int itemLevel, int maxStackSize,
			double price, int quantity, int requiredLevel)
			throws SQLException {
		final int itemId = ItemDao.create(cxn, itemName, itemLevel,
				maxStackSize, price, quantity);
		String insertGear = "INSERT INTO Gear (itemId, requiredLevel) VALUES (?, ?);";
		try (PreparedStatement insertStmt = cxn.prepareStatement(insertGear)) {
			insertStmt.setInt(1, itemId);
			insertStmt.setInt(2, requiredLevel);
			insertStmt.executeUpdate();
			return new Gear(itemId, itemName, itemLevel, maxStackSize, price,
					quantity, requiredLevel);
		}
	}

	public static Gear getGearByItemID(Connection cxn, int itemID)
			throws SQLException {
		String selectGear = "SELECT * FROM Gear WHERE itemID = ?;";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectGear)) {
			selectStmt.setInt(1, itemID);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new Gear(
							itemID, 
							results.getString("itemName"),
							results.getInt("itemLevel"),
							results.getInt("maxStackSize"),
							results.getInt("price"),
							results.getInt("quantity"),
							results.getInt("requiredLevel")
						);
				}
			}
		}
		return null;
	}

	public static List<Item> getGearsByRequiredLevel(Connection cxn,
			int requiredLevel) throws SQLException {
		final String selectGear = "SELECT * FROM Gear WHERE requiredLevel = ?;";
		List<Item> gearList = new ArrayList<>();
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectGear);
				ResultSet results = selectStmt.executeQuery()) {
			while (results.next()) {
				gearList.add(new Gear(results.getInt("itemID"),
						results.getString("itemName"),
						results.getInt("itemLevel"),
						results.getInt("maxStackSize"),
						results.getDouble("price"), results.getInt("quantity"),
						requiredLevel));
			}
			return gearList;
		}
	}

	/**
	 * Update the quantity of the Gear instance. Quantity only exists in Gear's
	 * superclass Item
	 */
	public static Gear updateQuantity(Connection cxn, Gear gear,
			int newQuantity) throws SQLException {
		return ItemDao.updateQuantity(cxn, gear, newQuantity);
	}

	public static void delete(Connection cxn, Gear gear) throws SQLException {
		ItemDao.delete(cxn, gear);
	}
}