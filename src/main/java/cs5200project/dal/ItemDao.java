package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Item;

public class ItemDao {
	private ItemDao() {
	}

	public static int create(Connection cxn, String itemName, int itemLevel,
			int maxStackSize, double price, int quantity) throws SQLException {
		final String insertItem = """
				    INSERT INTO Item (itemName, itemLevel, maxStackSize, price, quantity)
				    VALUES (?, ?, ?, ?, ?);
				""";

		try (PreparedStatement insertStmt = cxn.prepareStatement(insertItem,
				Statement.RETURN_GENERATED_KEYS)) {
			insertStmt.setString(1, itemName);
			insertStmt.setInt(2, itemLevel);
			insertStmt.setInt(3, maxStackSize);
			insertStmt.setDouble(4, price);
			insertStmt.setInt(5, quantity);
			insertStmt.executeUpdate();

			return Utils.getAutoIncrementKey(insertStmt);
		}
	}

	public static Item getItemById(Connection cxn, int itemId)
			throws SQLException {
		final String selectItem = "SELECT * FROM Item WHERE itemID = ?;";
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectItem)) {
			selectStmt.setInt(1, itemId);
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {
					return new Item(itemId, results.getString("itemName"),
							results.getInt("itemLevel"),
							results.getInt("maxStackSize"),
							results.getDouble("price"),
							results.getInt("quantity"));
				} else {
					return null;
				}
			}
		}
	}

	public static List<Item> getItemsByName(Connection cxn, String itemName)
			throws SQLException {
		final String selectItem = "SELECT * FROM Item WHERE itemName = ?;";
		List<Item> itemsList = new ArrayList<>();
		try (PreparedStatement selectStmt = cxn.prepareStatement(selectItem)) {
			try (ResultSet results = selectStmt.executeQuery()) {
				while (results.next()) {
					itemsList.add(new Item(results.getInt("itemID"), itemName,
							results.getInt("itemLevel"),
							results.getInt("maxStackSize"),
							results.getDouble("price"),
							results.getInt("quantity")));
				}
				return itemsList;
			}
		}
	}

	public static <T extends Item> T updateQuantity(Connection cxn, T item,
			int newQuantity) throws SQLException {
		final String update = "UPDATE Item SET quantity = ? WHERE itemID = ?;";
		try (PreparedStatement updateStmt = cxn.prepareStatement(update)) {
			updateStmt.setInt(1, newQuantity);
			updateStmt.setInt(2, item.getItemId());
			updateStmt.executeUpdate();

			item.setQuantity(newQuantity);
			return item;
		}
	}

	public static void delete(Connection cxn, Item item) throws SQLException {
		final String delete = "DELETE FROM Item WHERE itemID = ?;";
		try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, item.getItemId());
			stmt.executeUpdate();
		}
	}
}
