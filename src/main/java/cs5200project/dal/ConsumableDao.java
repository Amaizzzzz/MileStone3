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

    // Insert into Consumable table
    String query =
			"INSERT INTO `Consumable` (itemID, consumablesType, description, source) VALUES (?, ?, ?, ?)";
    
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
				       c.consumablesType, c.description, c.source
				FROM `Item` i
				JOIN `Consumable` c ON i.itemID = c.itemID
				WHERE i.itemID = ?
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
					rs.getString("description"),
					rs.getString("source")
          );
        }
      }
    }
    return null;
  }
  
  // Get all consumables of a specific type
  public static List<Consumable> getConsumablesByType(Connection cxn, ConsumablesType type) 
      throws SQLException {
    String query = """
            SELECT i.itemID, i.itemName, i.itemLevel, i.maxStackSize, i.price, i.quantity,
                   c.description, c.source
            FROM `Item` i
            JOIN `Consumable` c ON i.itemID = c.itemID
            WHERE c.consumablesType = ?
            """;
    
    List<Consumable> consumables = new ArrayList<>();
    
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
      stmt.setString(1, type.name());
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          consumables.add(new Consumable(
              rs.getInt("itemID"),
              rs.getString("itemName"),
              rs.getInt("itemLevel"),
              rs.getInt("maxStackSize"),
              rs.getDouble("price"),
              rs.getInt("quantity"),
              type,
              rs.getString("description"),
              rs.getString("source")
          ));
        }
      }
    }
    return consumables;
  }
  
  /**
   * Update the quantity of the Consumable instance. Quantity exists in the superclass Item
   */
  public static Consumable updateQuantity(Connection cxn, Consumable consumable, int newQuantity) 
      throws SQLException {
    return ItemDao.updateQuantity(cxn, consumable, newQuantity);
  }
  
  public static void delete(Connection cxn, Consumable consumable) throws SQLException {
    ItemDao.delete(cxn, consumable);
  }
}