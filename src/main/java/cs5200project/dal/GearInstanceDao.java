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

	public static GearInstance create(Connection cxn, Item item,
			Character character, GearSlot gearSlot) throws SQLException {
		String insertGearInstance = "INSERT INTO GearInstance (itemID, characterID, slotID) VALUES (?, ?, ?);";
		try (PreparedStatement stmt = cxn.prepareStatement(insertGearInstance,
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, item.getItemId());
			stmt.setInt(2, character.getCharacterID());
			stmt.setInt(3, gearSlot.getSlotID());
			stmt.executeUpdate();

			return new GearInstance(Utils.getAutoIncrementKey(stmt),
					item, character, gearSlot);
		}
	}

	public static List<GearInstance> getGearInstanceByItemID(Connection cxn,
			Item item) throws SQLException {
		String selectGearInstance = "SELECT * FROM GearInstance WHERE itemId = ?;";
		List<GearInstance> gearInstances = new ArrayList<>();
		try (PreparedStatement stmt = cxn
				.prepareStatement(selectGearInstance)) {
			stmt.setInt(1, item.getItemId());
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
									item, character, gearSlot));
				}
			}
		}
		return gearInstances;
	}
}