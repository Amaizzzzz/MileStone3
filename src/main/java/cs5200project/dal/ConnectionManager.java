package cs5200project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Use ConnectionManager to connect to your database instance.
 *
 * ConnectionManager uses the MySQL Connector/J driver to connect to
 * your local MySQL instance.
 *
 * In our example, we will create a DAO (data access object) java
 * class to interact with each MySQL table. The DAO java classes will
 * use ConnectionManager to open and close connections.
 *
 *
 * Before using, you must edit the values for PASSWORD and SCHEMA
 * below.  PASSWORD must be the MySQL password you configured when you
 * installed MySQL server, and SCHEMA must be the name of the database
 * schema that the application will use.
 */
public class ConnectionManager {

  /**
   * Private default constructor to prevent instantiation.
   */
  private ConnectionManager() { }

  // User to connect to your database instance. By default, this is "root2".
  private static final String USER = "root";
  // Password for the user.
  private static final String PASSWORD = "Cao123456789";
  // URI to your database server. If running on the same machine, then
  // this is "localhost".
  private static final String HOSTNAME = "localhost";
  // Port to your database server. By default, this is 3307.
  private static final int PORT = 3306;
  // Name of the MySQL schema that contains your tables.
  private static final String SCHEMA = "cs5200project";
  // Default timezone for MySQL server.
  private static final String TIMEZONE = "UTC";

  /** Get the connection to the database instance. */
  public static Connection getConnection() throws SQLException {
    Connection connection = null;
    try {
      Properties connectionProperties = new Properties();
      connectionProperties.put("user", USER);
      connectionProperties.put("password", PASSWORD);
      connectionProperties.put("serverTimezone", TIMEZONE);
      // Ensure the JDBC driver is loaded by retrieving the runtime
      // Class descriptor.  Otherwise, Tomcat may have issues loading
      // libraries in the proper order.  One alternative is calling
      // this in the HttpServlet init() override.
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException(e);
      }
      connection = DriverManager.getConnection(
        String.format(
          "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true",
          HOSTNAME,
          PORT,
          SCHEMA
        ),
        connectionProperties
      );
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return connection;
  }

  /**
   * Open and return a connection to the database server that is not
   * associated with a particular schema.  We use this for operations
   * that delete and re-create the schema; see Driver.java for an example.
   */
  public static Connection getSchemalessConnection() throws SQLException {
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", USER);
    connectionProperties.put("password", PASSWORD);
    connectionProperties.put("serverTimezone", TIMEZONE);
    return DriverManager.getConnection(
      String.format(
        "jdbc:mysql://%s:%d?useSSL=false&allowPublicKeyRetrieval=true",
        HOSTNAME,
        PORT
        ),
      connectionProperties
    );
  }
}
