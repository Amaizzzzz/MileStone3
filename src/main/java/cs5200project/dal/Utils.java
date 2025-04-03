package cs5200project.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * General utilities for data-access methods
 */
public class Utils {
  private Utils() { }

  /**
   * Converts a Java date value to a SQL timestamp.
   * @param d Date to be converted; must not be null
   * @return equivalent SQL timestamp value
   */
  public static Timestamp dateToTimestamp(Date d) {
    return new Timestamp(Objects.requireNonNull(d).getTime());
  }

  /**
   * Converts a SQL timestamp value to a Java date.
   * @param t Timestamp to be converted; must not be null
   * @return equivalent Java date value
   */
  public static Date timestampToDate(Timestamp t) {
    return new Date(Objects.requireNonNull(t).getTime());
  }

  /**
   * Returns a SQL Timestamp value representing the current time.
   */
  public static Timestamp currentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * Retrieves the value of the AUTO_INCREMENT key for the first record
   * inserted by the given statement.
   * @param insertStmt statement that inserted one or more records
   * @return Value of the primary key for the newly-created record
   * @throws SQLException if we cannot retrieve the key value
   */
  public static int getAutoIncrementKey(
    final PreparedStatement insertStmt
  ) throws SQLException {
    try (ResultSet rs = insertStmt.getGeneratedKeys()) {
      if (rs.next()) {
        return rs.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key");
      }
    }
  }
}
