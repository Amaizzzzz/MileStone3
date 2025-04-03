package cs5200project.dal;

import cs5200project.model.Consumables;

import cs5200project.model.Weapon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumablesDao {

  private ConsumablesDao() {
    // Prevent instantiation
  }

  // Create a consumable record
  public static Consumables create(Connection cxn, String consumablesName, Consumables.ConsumablesType consumablesType,
      String consumablesDescription, String source) throws SQLException {
    String query =
        "INSERT INTO Consumables (consumablesName, consumablesType, consumablesDescription, source) "
            +
            "VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
      stmt.setString(1, consumablesName);
      stmt.setString(2, consumablesType.name()); // Enum stored as string
      stmt.setString(3, consumablesDescription);
      stmt.setString(4, source);

      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating consumables failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int itemID = generatedKeys.getInt(1);
          return new Consumables(itemID, consumablesName, consumablesType, consumablesDescription,
              source);
        } else {
          throw new SQLException("Creating consumables failed, no ID obtained.");
        }
      }
    }
  }

  // Fetch a consumable by item ID
  public static Consumables getByConsumablesId(Connection cxn, int itemID) throws SQLException {
    String query = "SELECT * FROM Consumables WHERE itemID = ?";
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
      stmt.setInt(1, itemID);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new Consumables(
              rs.getInt("itemID"),
              rs.getString("consumablesName"),
              Consumables.ConsumablesType.valueOf(rs.getString("consumablesType")),
              rs.getString("consumablesDescription"),
              rs.getString("source")
          );
        }
      }
    }
    return null;
  }
}