package cs5200project.dal;

import cs5200project.model.Weapon.RankValue;
import cs5200project.model.Weapon.WeaponDurability;
import cs5200project.model.Weapon.WeaponType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cs5200project.model.Weapon;

public class WeaponDao {
  // Dao classes should not be instantiated.
  // Pass Connection object as parameter in each method
  // Each method should be static
  private WeaponDao() {
    // Private constructor to prevent instantiation
  }

  public static Weapon create(Connection cxn, String weaponName, Weapon.WeaponType weaponType, int gearSlotID, int jobID,
      int requiredLevel, int damage, Weapon.WeaponDurability weaponDurability, Weapon.RankValue rankValue) throws SQLException {
    String query = "INSERT INTO Weapon (weaponName, weaponType, gearSlotID, jobID, requiredLevel, damage, weaponDurability, rankValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = cxn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, weaponName);
      stmt.setString(2, weaponType.name());
      stmt.setInt(3, gearSlotID);
      stmt.setInt(4, jobID);
      stmt.setInt(5, requiredLevel);
      stmt.setInt(6, damage);
      stmt.setString(7, weaponDurability.name());
      stmt.setString(8, rankValue.name());

      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating weapon failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int itemID = generatedKeys.getInt(1);
          return new Weapon(itemID, weaponName, weaponType, gearSlotID,
              jobID, requiredLevel, damage, weaponDurability, rankValue);
        } else {
          throw new SQLException("Creating weapon failed, no ID obtained.");
        }
      }
    }
  }

  public static Weapon getWeaponById(Connection cxn, int id) throws SQLException {
    String query = "SELECT * FROM Weapon WHERE itemID = ?";
    try (PreparedStatement stmt = cxn.prepareStatement(query)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Weapon.WeaponType weaponType = Weapon.WeaponType.valueOf(rs.getString("weaponType"));
          Weapon.WeaponDurability weaponDurability = Weapon.WeaponDurability.valueOf(rs.getString("weaponDurability"));
          Weapon.RankValue rankValue = Weapon.RankValue.valueOf(rs.getString("rankValue"));
          return new Weapon(
              rs.getInt("itemID"),
              rs.getString("weaponName"),
              weaponType,
              rs.getInt("gearSlotID"),
              rs.getInt("jobID"),
              rs.getInt("requiredLevel"),
              rs.getInt("damage"),
              weaponDurability,
              rankValue
          );
        }
      }
    }
    return null;
  }
}