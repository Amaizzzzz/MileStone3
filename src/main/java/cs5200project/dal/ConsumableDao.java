package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Consumable;
import cs5200project.model.Consumable.ConsumablesType;
import cs5200project.model.Item;

public class ConsumableDao {

	private ConsumableDao() {
    // Prevent instantiation
  }

  // Create a consumable record
	public static Consumable create(Connection cxn, 
			int itemID, String itemName, int itemLevel, int maxStackSize, double price,
			int quantity, ConsumablesType consumablesType,
      String consumablesDescription, String source) throws SQLException {
    
		// If itemID is 0, create a new Item in the parent table
		// and get back the auto-generated ID
		if (itemID == 0) {
			itemID = ItemDao.create(cxn, itemName, itemLevel, maxStackSize, price, quantity);
		}

    // Insert into Consumables table
    String query =
			"INSERT INTO Consumables (itemID, consumablesType, consumablesDescription, source) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
		stmt.setInt(1, itemID);
		stmt.setString(2, consumablesType.name()); // Enum stored as string
		stmt.setString(3, consumablesDescription);
		stmt.setString(4, source);
		stmt.executeUpdate();
		
		return new Consumable(itemID, itemName, itemLevel, maxStackSize, price,
				quantity, consumablesType, consumablesDescription, source);
    }
  }

  // Fetch a consumable by item ID
	public static Consumable getByConsumablesId(Connection cxn, int itemID)
			throws SQLException {
		String query = """
				SELECT i.itemName, i.itemLevel, i.maxStackSize, i.price, i.quantity,
				       c.consumablesType, c.consumablesDescription, c.source
				FROM Item i
				JOIN Consumables c ON i.itemID = c.itemID
				WHERE i.itemID = ?;
				""";
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
			return new Consumable(
					itemID,
					rs.getString("itemName"), 
					rs.getInt("itemLevel"),
					rs.getInt("maxStackSize"), 
					rs.getDouble("price"),
					rs.getInt("quantity"),
					ConsumablesType.valueOf(rs.getString("consumablesType")),
					rs.getString("consumablesDescription"),
					rs.getString("source")
          );
        }
      }
    }
    return null;
  }
  
  
  public static void delete(Connection cxn, Consumable consumable) throws SQLException {
    ItemDao.delete(cxn, consumable);
  }
}