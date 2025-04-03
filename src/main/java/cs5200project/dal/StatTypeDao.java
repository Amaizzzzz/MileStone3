package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.StatType;

public class StatTypeDao {

	/**
	 * Inserts a new record into the StatType table.
	 * 
	 * @param cxn         The database connection
	 * @param name        The name of the stat type
	 * @param description A description or additional info
	 * @return The newly inserted StatType object
	 */
	public static StatType create(Connection cxn, String name,
			String description) throws SQLException {
		String sql = "INSERT INTO `StatType` (statType, description) VALUES (?, ?)";
		try (PreparedStatement ps = cxn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setString(2, description);
			ps.executeUpdate();

			return new StatType(Utils.getAutoIncrementKey(ps), name, description);
		}
	}

	/**
	 * Retrieves a StatType record by its primary key (statTypeID).
	 * 
	 * @param cxn        The database connection
	 * @param statTypeID The primary key to look up
	 * @return The matching StatType object, or null if not found
	 */
	public static StatType getStatTypeByID(Connection cxn, int statTypeID)
			throws SQLException {
		String sql = "SELECT statTypeID, statType, description FROM StatType WHERE statTypeID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, statTypeID);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					StatType st = new StatType();
					st.setStatTypeID(rs.getInt("statTypeID"));
					st.setName(rs.getString("statType"));
					st.setDescription(rs.getString("description"));
					return st;
				}
			}
		}
		return null;
	}

	/**
	 * Updates the description field of a given StatType record.
	 * 
	 * @param cxn            The database connection
	 * @param statType       The StatType object to update (must have a valid
	 *                       statTypeID)
	 * @param newDescription The new description value
	 * @return The updated StatType object
	 */
	public static StatType updateDescription(Connection cxn, StatType statType,
			String newDescription) throws SQLException {
		String sql = "UPDATE StatType SET description = ? WHERE statTypeID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setString(1, newDescription);
			ps.setInt(2, statType.getStatTypeID());
			ps.executeUpdate();
		}
		// Sync the Java object with the new value
		statType.setDescription(newDescription);
		return statType;
	}

	/**
	 * Deletes a StatType record from the database.
	 * 
	 * @param cxn      The database connection
	 * @param statType The StatType object to delete (must have a valid
	 *                 statTypeID)
	 */
	public static void delete(Connection cxn, StatType statType)
			throws SQLException {
		String sql = "DELETE FROM StatType WHERE statTypeID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, statType.getStatTypeID());
			ps.executeUpdate();
		}
	}

	/**
	 * Retrieves a list of StatType records whose name contains a given
	 * substring.
	 * 
	 * @param cxn         The database connection
	 * @param partialName The substring to match against the 'name' column
	 * @return A list of matching StatType objects (could be empty if no match)
	 */
	public static List<StatType> getStatTypeByName(Connection cxn,
			String partialName) throws SQLException {
		String sql = "SELECT statTypeID, statType, description FROM StatType WHERE statType LIKE ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setString(1, "%" + partialName + "%");
			try (ResultSet rs = ps.executeQuery()) {
				List<StatType> result = new ArrayList<>();
				while (rs.next()) {
					StatType st = new StatType();
					st.setStatTypeID(rs.getInt("statTypeID"));
					st.setName(rs.getString("statType"));
					st.setDescription(rs.getString("description"));
					result.add(st);
				}
				return result;
			}
		}
	}
}