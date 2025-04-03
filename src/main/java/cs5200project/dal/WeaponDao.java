package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs5200project.model.Weapon;
import cs5200project.model.Weapon.RankValue;
import cs5200project.model.Weapon.WeaponDurability;
import cs5200project.model.GearSlot;
import cs5200project.model.Item;
import cs5200project.model.Job;

public class WeaponDao {

	private WeaponDao() {
		// Prevent instantiation
	}

	
	public static Weapon create(Connection cxn, 
			int itemID, String itemName, int itemLevel, int maxStackSize,
			double price, int quantity, int requiredLevel, int damage,
			int attackSpeed, String weaponType, GearSlot gearSlot, Job requiredJob,
			WeaponDurability weaponDurability, RankValue rankValue) 
			throws SQLException {

		// If itemID is 0, create a new Item in the parent table
		// and get back the auto-generated ID
		if (itemID == 0) {
			itemID = ItemDao.create(cxn, itemName, itemLevel, maxStackSize, price, quantity);
		}

		// Insert into Weapon table
		final String insertWeapon = """
			INSERT INTO `Weapon` (itemID, requiredLevel, damage, attackSpeed, weaponType, gearSlotID, jobID, weaponDurability, rankValue)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";
			
		try (PreparedStatement stmt = cxn.prepareStatement(insertWeapon)) {
			stmt.setInt(1, itemID);
			stmt.setInt(2, requiredLevel);
			stmt.setInt(3, damage);
			stmt.setInt(4, attackSpeed);
			stmt.setString(5, weaponType);
			stmt.setInt(6, gearSlot.getSlotID());
			stmt.setInt(7, requiredJob.getJobID());
			stmt.setString(8, weaponDurability.name());
			stmt.setString(9, rankValue.name());
			stmt.executeUpdate();
		}

		// Return a new Weapon object with the generated itemID
		return new Weapon(itemID, itemName, itemLevel, maxStackSize, price,
				quantity, requiredLevel, damage, attackSpeed, weaponType, 
				requiredJob.getJobName(), weaponDurability, rankValue);
	}


	public static Weapon getWeaponById(Connection cxn, int itemID)
			throws SQLException {
		String query = """
				SELECT i.itemName, i.itemLevel, i.maxStackSize, i.price, i.quantity,
				       w.requiredLevel, w.damage, w.attackSpeed, w.weaponType, 
				       w.gearSlotID, w.jobID, w.weaponDurability, w.rankValue
				FROM `Weapon` w
				JOIN `Item` i ON w.itemID = i.itemID
				WHERE w.itemID = ?
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
					int requiredLevel = rs.getInt("requiredLevel");
					int damage = rs.getInt("damage");
					int attackSpeed = rs.getInt("attackSpeed");
					String weaponType = rs.getString("weaponType");
					
					// Get the required job name from the Job table
					int requiredJobID = rs.getInt("jobID");
					Job job = JobDao.getJobById(cxn, requiredJobID);
					String requiredJobName = job != null ? job.getJobName() : "";
					
					WeaponDurability weaponDurability = WeaponDurability
							.valueOf(rs.getString("weaponDurability"));
					RankValue rankValue = RankValue
							.valueOf(rs.getString("rankValue"));

					return new Weapon(itemID, itemName, itemLevel, maxStackSize,
							price, quantity, requiredLevel, damage, attackSpeed, weaponType,
							requiredJobName, weaponDurability, rankValue);
				}
			}
        }
		return null;
    }
}
