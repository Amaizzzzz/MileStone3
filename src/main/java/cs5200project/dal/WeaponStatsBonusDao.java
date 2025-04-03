package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.WeaponStatsBonus;
import cs5200project.model.Item;
import cs5200project.model.Statistic;

public class WeaponStatsBonusDao {
  private WeaponStatsBonusDao() {
  }

  public static WeaponStatsBonus create(Connection cxn, Item item,
      Statistic stats, int bonusValue) throws SQLException {
    String insertBonus = """
				INSERT INTO WeaponStatsBonus (itemID, statID, bonusValue)
				VALUES (?, ?, ?);""";
    try (PreparedStatement insertStmt = cxn.prepareStatement(insertBonus)) {
      insertStmt.setInt(1, item.getItemId());
      insertStmt.setInt(2, stats.getStatisticID());
      insertStmt.setInt(3, bonusValue);
      insertStmt.executeUpdate();
      return new WeaponStatsBonus(item, stats, bonusValue);
    }
  }

  public static WeaponStatsBonus getWeaponStatsBonusByItemIdAndStatId(
      Connection cxn, Item item, Statistic stats) throws SQLException {
    String selectBonus = """
				SELECT * FROM WeaponStatsBonus
					WHERE itemID = ? AND statID = ?;
				""";
    try (PreparedStatement selectStmt = cxn.prepareStatement(selectBonus)) {
      selectStmt.setInt(1, item.getItemId());
      selectStmt.setInt(2, stats.getStatisticID());

      try (ResultSet results = selectStmt.executeQuery()) {
        if (results.next()) {
          return new WeaponStatsBonus(item, stats,
              results.getInt("bonusValue"));
        } else {
          return null;
        }
      }
    }
  }

  public static List<WeaponStatsBonus> getWeaponStatsBonusByBonusValue(
      Connection cxn,
      int bonusValue) throws SQLException {
    String selectBonus = "SELECT * FROM GearStatisticBonus WHERE bonusValue = ?;";
    List<WeaponStatsBonus> bonusList = new ArrayList<>();
    try (PreparedStatement stmt = cxn.prepareStatement(selectBonus)) {
      stmt.setInt(1, bonusValue);
      try (ResultSet results = stmt.executeQuery()) {
        while (results.next()) {
          int itemId = results.getInt("itemID");
          Item item = ItemDao.getItemById(cxn, itemId);
          int statId = results.getInt("statID");
          Statistic stats = StatisticDao.getStatisticByID(cxn,
              statId);
          bonusList.add(new WeaponStatsBonus(
              item, stats, results.getInt("bonusValue")));
        }
      }
    }
    return bonusList;
  }
}