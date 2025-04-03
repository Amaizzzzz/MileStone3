package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.WeaponStatsBonus;
import cs5200project.model.Weapon;
import cs5200project.model.Weapon.RankValue;
import cs5200project.model.Weapon.WeaponDurability;
import cs5200project.model.GearSlot;
import cs5200project.model.Item;
import cs5200project.model.Job;
import cs5200project.model.Statistic;

public class WeaponStatsBonusDao {
  private WeaponStatsBonusDao() {
  }

  public static WeaponStatsBonus create(Connection cxn, Weapon weapon,
      Statistic stats, int bonusValue) throws SQLException {
    String insertBonus = """
				INSERT INTO WeaponStatsBonus (itemID, statID, bonusValue)
				VALUES (?, ?, ?);""";
    try (PreparedStatement insertStmt = cxn.prepareStatement(insertBonus)) {
      insertStmt.setInt(1, weapon.getItemId());
      insertStmt.setInt(2, stats.getStatisticID());
      insertStmt.setInt(3, bonusValue);
      insertStmt.executeUpdate();
      return new WeaponStatsBonus(weapon, stats, bonusValue);
    }
  }

  public static WeaponStatsBonus getWeaponStatsBonusByItemIdAndStatId(
      Connection cxn, Weapon weapon, Statistic stats) throws SQLException {
    String selectBonus = """
				SELECT * FROM WeaponStatsBonus
					WHERE itemID = ? AND statID = ?;
				""";
    try (PreparedStatement selectStmt = cxn.prepareStatement(selectBonus)) {
      selectStmt.setInt(1, weapon.getItemId());
      selectStmt.setInt(2, stats.getStatisticID());

      try (ResultSet results = selectStmt.executeQuery()) {
        if (results.next()) {
          return new WeaponStatsBonus(weapon, stats,
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
          // Get the full weapon object by its ID
          Weapon weapon = WeaponDao.getWeaponById(cxn, itemId);
          
          int statId = results.getInt("statID");
          Statistic stats = StatisticDao.getStatisticByID(cxn, statId);
          
          bonusList.add(new WeaponStatsBonus(weapon, stats, bonusValue));
        }
      }
    }
    return bonusList;
  }
}