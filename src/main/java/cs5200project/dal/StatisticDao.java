package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Statistic;

public class StatisticDao {

	public static Statistic create(Connection cxn, int statTypeID, int statValue) throws SQLException {
		String sql = "INSERT INTO `Statistic` (statTypeID, statValue) VALUES (?, ?)";
		try (PreparedStatement ps = cxn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, statTypeID);
			ps.setInt(2, statValue);
			ps.executeUpdate();
			return new Statistic(Utils.getAutoIncrementKey(ps), statValue, statTypeID);
		}
	}

	/**
	 * Retrieves a Statistic record by its primary key.
	 * 
	 * @param cxn         The database connection
	 * @param statisticID The ID to look up
	 * @return A matching Statistic object, or null if not found
	 * @throws SQLException
	 */
	public static Statistic getStatisticByID(Connection cxn, int statisticID)
			throws SQLException {
		String sql = "SELECT statisticID, statValue, statTypeID FROM Statistic WHERE statisticID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, statisticID);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Statistic s = new Statistic();
					s.setStatisticID(rs.getInt("statisticID"));
					s.setStatValue(rs.getInt("statValue"));
					s.setStatTypeID(rs.getInt("statTypeID"));
					return s;
				}
			}
		}
		return null;
	}

	public static Statistic updateStatValue(Connection cxn, Statistic stat,
			int newValue) throws SQLException {
		String sql = "UPDATE Statistic SET statValue = ? WHERE statisticID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, newValue);
			ps.setInt(2, stat.getStatisticID());
			ps.executeUpdate();
		}
		// Update the Java object
		stat.setStatValue(newValue);
		return stat;
	}

	public static void delete(Connection cxn, Statistic stat)
			throws SQLException {
		String sql = "DELETE FROM Statistic WHERE statisticID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, stat.getStatisticID());
			ps.executeUpdate();
		}
	}

	public static List<Statistic> getAllByStatType(Connection cxn,
			int statTypeID) throws SQLException {
		String sql = "SELECT statisticID, statValue, statTypeID FROM Statistic WHERE statTypeID = ?";
		try (PreparedStatement ps = cxn.prepareStatement(sql)) {
			ps.setInt(1, statTypeID);
			try (ResultSet rs = ps.executeQuery()) {
				List<Statistic> result = new ArrayList<>();
				while (rs.next()) {
					Statistic s = new Statistic();
					s.setStatisticID(rs.getInt("statisticID"));
					s.setStatValue(rs.getInt("statValue"));
					s.setStatTypeID(rs.getInt("statTypeID"));
					result.add(s);
				}
				return result;
			}
		}
	}
}