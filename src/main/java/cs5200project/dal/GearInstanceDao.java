package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Character;
import cs5200project.model.GearInstance;
import cs5200project.model.GearSlot;
import cs5200project.model.Item;

public class GearInstanceDao {
	private GearInstanceDao() {
	}

	public static GearInstance create(Connection cxn, Gear gear,
			Character character, GearSlot gearSlot) throws SQLException {
		String insertGearInstance = """
				INSERT INTO GearInstance (itemID, characterID, slotID)
					VALUES (?, ?, ?);""";
		try (PreparedStatement stmt = cxn.prepareStatement(insertGearInstance,
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, gear.getItemId());
			stmt.setInt(2, character.getCharacterID());
			stmt.setInt(3, gearSlot.getSlotID());
			stmt.executeUpdate();
			
			return new GearInstance(Utils.getAutoIncrementKey(stmt), gearSlot, character, gear);
		}
	}

	public static GearInstance getGearInstanceByGearInstanceId(
			Connection cxn,
			GearInstance gearInstance
	) throws SQLException {
		String selectInstance = "SELECT * FROM GearInstance WHERE gearInstanceID = ?;";
		try (PreparedStatement selectStmt = cxn
				.prepareStatement(selectInstance)) {
			selectStmt.setInt(1, gearInstance.getGearInstanceID());
			try (ResultSet results = selectStmt.executeQuery()) {
				if (results.next()) {

					GearSlot slot = GearSlotDao.getGearSlotById(cxn,
							results.getInt("slotID"));
					/* **CharacterDao needs fix** */
					Character character = CharacterDao.getCharacterById(cxn,
							results.getInt("characterID"));
					Gear gear = GearDao.getGearByItemID(cxn,
							results.getInt("itemID"));
					return new GearInstance(
							gearInstance.getGearInstanceID(),
							slot, character, gear
					);
				}
				else {
					return null;
				}
			}
		}
	}

	public static List<GearInstance> getGearInstanceByItemID(Connection cxn,
			Gear gear) throws SQLException {
		String selectGearInstance = "SELECT * FROM GearInstance WHERE itemId = ?;";
		List<GearInstance> gearInstances = new ArrayList<>();
		try (PreparedStatement stmt = cxn
				.prepareStatement(selectGearInstance)) {
			stmt.setInt(1, gear.getItemId());
			try (ResultSet results = stmt.executeQuery()) {
				while (results.next()) {
					int characterID = results.getInt("characterID");
					int slotID = results.getInt("slotID");

					/* *** CharacterDao.getCharacterById needs fix *** */
					Character character = CharacterDao.getCharacterById(cxn,
							characterID);
					GearSlot gearSlot = GearSlotDao.getGearSlotById(cxn,
							slotID);

					gearInstances.add(
							new GearInstance(results.getInt("gearInstanceID"),
									gearSlot, character, gxxear));
				}
			}
		}
		return gearInstances;
	}
}