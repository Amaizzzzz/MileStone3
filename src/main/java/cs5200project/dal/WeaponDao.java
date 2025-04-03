package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs5200project.model.Weapon;
import cs5200project.model.Weapon.RankValue;
import cs5200project.model.Weapon.WeaponDurability;

public class WeaponDao {

	private WeaponDao() {
		// Prevent instantiation
	}

	public static Weapon create(Connection cxn, String itemName, int itemLevel,
			int maxStackSize, double price, int quantity, int damage,
			int attackSpeed, String weaponType, String requiredJob,
			WeaponDurability weaponDurability, RankValue rankValue)
			throws SQLException {

		// 1. Insert into Item
		String insertItem = "INSERT INTO Item (itemName, itemLevel, maxStackSize, price, quantity) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmtItem = cxn.prepareStatement(insertItem,
				Statement.RETURN_GENERATED_KEYS)) {
			stmtItem.setString(1, itemName);
			stmtItem.setInt(2, itemLevel);
			stmtItem.setInt(3, maxStackSize);
			stmtItem.setDouble(4, price);
			stmtItem.setInt(5, quantity);

			stmtItem.executeUpdate();
			ResultSet rsItem = stmtItem.getGeneratedKeys();

			if (rsItem.next()) {
				int itemID = rsItem.getInt(1);

				// 2. Insert into Weapon
				String insertWeapon = """
						INSERT INTO Weapon (itemID, damage, attackSpeed, weaponType, requiredJob, weaponDurability, rankValue)
						VALUES (?, ?, ?, ?, ?, ?, ?)
						""";
				try (PreparedStatement stmtWeapon = cxn
						.prepareStatement(insertWeapon)) {
					stmtWeapon.setInt(1, itemID);
					stmtWeapon.setInt(2, damage);
					stmtWeapon.setInt(3, attackSpeed);
					stmtWeapon.setString(4, weaponType);
					stmtWeapon.setString(5, requiredJob);
					stmtWeapon.setString(6, weaponDurability.name());
					stmtWeapon.setString(7, rankValue.name());

					stmtWeapon.executeUpdate();
				}

				return new Weapon(itemID, itemName, itemLevel, maxStackSize,
						price, quantity, 0, damage, attackSpeed, weaponType,
						requiredJob, weaponDurability, rankValue);
			} else {
				throw new SQLException("Creating item failed, no ID returned.");
			}
        }
    }

	public static Weapon getWeaponById(Connection cxn, int itemID)
			throws SQLException {
		String query = """
				SELECT i.itemName, i.itemLevel, i.maxStackSize, i.price, i.quantity,
				       w.damage, w.attackSpeed, w.weaponType, w.requiredJob,
				       w.weaponDurability, w.rankValue
				FROM Item i
				JOIN Weapon w ON i.itemID = w.itemID
				WHERE i.itemID = ?
				""";

		try (PreparedStatement stmt = cxn.prepareStatement(query)) {
			stmt.setInt(1, itemID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String itemName = rs.getString("itemName");
					int itemLevel = rs.getInt("itemLevel");
					int maxStackSize = rs.getInt("maxStackSize");
					double price = rs.getDouble("price");
					int quantity = rs.getInt("quantity");
					int damage = rs.getInt("damage");
					int attackSpeed = rs.getInt("attackSpeed");
					String weaponType = rs.getString("weaponType");
					String requiredJob = rs.getString("requiredJob");
					WeaponDurability durability = WeaponDurability
							.valueOf(rs.getString("weaponDurability"));
					RankValue rank = RankValue
							.valueOf(rs.getString("rankValue"));

					return new Weapon(itemID, itemName, itemLevel, maxStackSize,
							price, quantity, 0, damage, attackSpeed, weaponType,
							requiredJob, durability, rank);
				}
			}
        }
		return null;
    }
}
