
package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Player;

public class PlayerDao {

	public static Player getPlayerByID(Connection cxn, int playerID)
			throws SQLException {
		String sql = "SELECT playerID, username, email, serverRegion FROM Player WHERE playerID = ?";
		PreparedStatement ps = cxn.prepareStatement(sql);
		ps.setInt(1, playerID);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			Player player = new Player();
			player.setPlayerID(rs.getInt("playerID"));
			player.setUsername(rs.getString("username"));
			player.setEmail(rs.getString("email"));
			player.setServerRegion(rs.getString("serverRegion"));
			return player;
		} else {
			return null;
		}
	}

	public static Player create(Connection cxn, String username, String email,
			String serverRegion) throws SQLException {
		String sql = "INSERT INTO Player(username, email, serverRegion) VALUES (?, ?, ?)";
		PreparedStatement ps = cxn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, username);
		ps.setString(2, email);
		ps.setString(3, serverRegion);
		ps.executeUpdate();

		return new Player(Utils.getAutoIncrementKey(ps), username, email, serverRegion);
	}

	public static Player updateUsername(Connection cxn, Player player,
			String newUsername) throws SQLException {
		String sql = "UPDATE Player SET username = ? WHERE playerID = ?";
		PreparedStatement ps = cxn.prepareStatement(sql);
		ps.setString(1, newUsername);
		ps.setInt(2, player.getPlayerID());
		ps.executeUpdate();

		player.setUsername(newUsername);
		return player;
	}

	public static void delete(Connection cxn, Player player)
			throws SQLException {
		String sql = "DELETE FROM Player WHERE playerID = ?";	
		PreparedStatement ps = cxn.prepareStatement(sql);
		ps.setInt(1, player.getPlayerID());
		ps.executeUpdate();
	}

	public static List<Player> getPlayersByPartialName(Connection cxn,
			String partialName) throws SQLException {
		String sql = "SELECT playerID, username, email, serverRegion FROM Player WHERE username LIKE ?";
		PreparedStatement ps = cxn.prepareStatement(sql);
		ps.setString(1, "%" + partialName + "%");
		ResultSet rs = ps.executeQuery();

		List<Player> result = new ArrayList<>();
		while (rs.next()) {
			Player p = new Player();
			p.setPlayerID(rs.getInt("playerID"));
			p.setUsername(rs.getString("username"));
			p.setEmail(rs.getString("email"));
			p.setServerRegion(rs.getString("serverRegion"));
			result.add(p);
		}
		return result;
	}
}
