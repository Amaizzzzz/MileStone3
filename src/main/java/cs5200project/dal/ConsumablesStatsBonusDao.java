package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.ConsumablesStatsBonus;
import cs5200project.model.Consumable;
import cs5200project.model.Statistic;

public class ConsumablesStatsBonusDao {

  private ConsumablesStatsBonusDao() {
    // Prevent instantiation
  }

  public static ConsumablesStatsBonus create(Connection cxn, Consumable item,
      Statistic stats, float percentageBonus, int bonusCap) throws SQLException {
    String insertBonus = """
                INSERT INTO ConsumablesStatsBonus (itemID, statID, percentageBonus, bonusCap)
                VALUES (?, ?, ?, ?);
                """;
    try (PreparedStatement insertStmt = cxn.prepareStatement(insertBonus)) {
      insertStmt.setInt(1, item.getItemId());
      insertStmt.setInt(2, stats.getStatisticID());
      insertStmt.setFloat(3, percentageBonus);
      insertStmt.setInt(4, bonusCap);
      insertStmt.executeUpdate();

      return new ConsumablesStatsBonus(item, stats, percentageBonus, bonusCap);
    }
  }

  public static ConsumablesStatsBonus getByItemIdAndStatId(Connection cxn, Consumable item, Statistic stats) throws SQLException {
    String selectBonus = """
                SELECT * FROM ConsumablesStatsBonus
                WHERE itemID = ? AND statID = ?;
                """;
    try (PreparedStatement selectStmt = cxn.prepareStatement(selectBonus)) {
      selectStmt.setInt(1, item.getItemId());
      selectStmt.setInt(2, stats.getStatisticID());

      try (ResultSet results = selectStmt.executeQuery()) {
        if (results.next()) {
          float percentageBonus = results.getFloat("percentageBonus");
          int bonusCap = results.getInt("bonusCap");
          return new ConsumablesStatsBonus(item, stats, percentageBonus, bonusCap);
        } else {
          return null;
        }
      }
    }
  }

  public static List<ConsumablesStatsBonus> getByBonusCap(Connection cxn, int bonusCap) throws SQLException {
    String selectBonus = "SELECT * FROM ConsumablesStatsBonus WHERE bonusCap = ?;";
    List<ConsumablesStatsBonus> bonusList = new ArrayList<>();

    try (PreparedStatement stmt = cxn.prepareStatement(selectBonus)) {
      stmt.setInt(1, bonusCap);
      try (ResultSet results = stmt.executeQuery()) {
        while (results.next()) {
          int itemId = results.getInt("itemID");
          Consumable item = ConsumableDao.getByConsumablesId(cxn, itemId);
          int statId = results.getInt("statID");
          Statistic stats = StatisticDao.getStatisticByID(cxn, statId);
          float percentageBonus = results.getFloat("percentageBonus");
          bonusList.add(new ConsumablesStatsBonus(item, stats, percentageBonus, bonusCap));
        }
      }
    }

    return bonusList;
  }
}