package cs5200project.driver;

import dao.PlayerDao;
import dao.StatTypeDao;
import dao.StatisticDao;
import tools.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import cs5200project.model.Player;
import cs5200project.model.StatType;
import cs5200project.model.Statistic;

public class Driver {
    public static void main(String[] args) {
        Connection schemalessConn = null;
        Connection conn = null;

        try {
            // 1) SCHEMA NAME - from ConnectionManager or manually define
            //    In your ConnectionManager, you might have SCHEMA = "CS5200Project".
            //    We'll assume that's the name we want to drop & create.
            String mySchemaName = "CS5200Project";
            
            // 2) Drop & Create DB using schemaless connection
            System.out.println("\n===== STEP 1: DROP & CREATE DATABASE =====");
            schemalessConn = ConnectionManager.getSchemalessConnection();
            dropAndCreateDatabase(schemalessConn, mySchemaName);
            schemalessConn.close();
            System.out.println("Successfully dropped and created database: " + mySchemaName);

            // 3) Now connect to the newly created schema
            System.out.println("\n===== STEP 2: CONNECT TO " + mySchemaName + " =====");
            conn = ConnectionManager.getConnection(); 
            // (In ConnectionManager, getConnection() presumably sets SCHEMA = "CS5200Project")

            // 4) Create all required tables
            System.out.println("\n===== STEP 3: CREATE TABLES =====");
            createTables(conn);
            System.out.println("All tables created (or already exist).");

            // 5) TEST DAO: PlayerDao
            System.out.println("\n===== STEP 4: TEST PlayerDao =====");
            Player p1 = PlayerDao.create(conn, "AlphaUser", "alpha@example.com", "NA-East");
            System.out.println("Player created with ID=" + p1.getPlayerID());

            Player fetchedP1 = PlayerDao.getPlayerByID(conn, p1.getPlayerID());
            if (fetchedP1 != null) {
                System.out.println("Fetched Player: " + fetchedP1.getUsername());
            }

            PlayerDao.updateUsername(conn, fetchedP1, "BetaUser");
            System.out.println("Updated Player username to: " + fetchedP1.getUsername());

            // advanced query example? E.g. get player by partial username
            System.out.println("Players whose name includes 'Beta':");
            for (Player pl : PlayerDao.getPlayersByPartialName(conn, "Beta")) {
                System.out.println(" - " + pl.getUsername());
            }

            // 6) TEST DAO: StatTypeDao
            System.out.println("\n===== STEP 5: TEST StatTypeDao =====");
            StatType st1 = StatTypeDao.create(conn, "HP", "Hit Points");
            System.out.println("StatType created with ID=" + st1.getStatTypeID());

            StatType fetchedST1 = StatTypeDao.getStatTypeByID(conn, st1.getStatTypeID());
            if (fetchedST1 != null) {
                System.out.println("Fetched StatType: " + fetchedST1.getName() 
                    + " desc=" + fetchedST1.getDescription());
            }

            // update
            StatTypeDao.updateDescription(conn, fetchedST1, "Health Points");
            System.out.println("Updated StatType description to: " + fetchedST1.getDescription());

            // advanced query example
            System.out.println("Searching StatTypes that contain 'Health':");
            for (StatType sType : StatTypeDao.getStatTypeByName(conn, "Health")) {
                System.out.println(" - ID=" + sType.getStatTypeID() + " name=" + sType.getName());
            }

            // 7) TEST DAO: StatisticDao
            System.out.println("\n===== STEP 6: TEST StatisticDao =====");
            // create
            Statistic stat1 = StatisticDao.create(conn, 100, st1.getStatTypeID());
            System.out.println("Statistic created with ID=" + stat1.getStatisticID() 
                + ", statValue=" + stat1.getStatValue());

            // retrieve
            Statistic fetchedStat1 = StatisticDao.getStatisticByID(conn, stat1.getStatisticID());
            if (fetchedStat1 != null) {
                System.out.println("Fetched Statistic: " + fetchedStat1.getStatisticID() 
                    + ", statValue=" + fetchedStat1.getStatValue());
            }

            // update
            StatisticDao.updateStatValue(conn, fetchedStat1, 120);
            System.out.println("Updated Statistic value to: " + fetchedStat1.getStatValue());

            // advanced query example
            System.out.println("All Statistic with statTypeID=" + st1.getStatTypeID() + ":");
            for (Statistic st : StatisticDao.getAllByStatType(conn, st1.getStatTypeID())) {
                System.out.println(" - statisticID=" + st.getStatisticID() 
                    + ", value=" + st.getStatValue());
            }

            // 8) You can continue with other tables: RaceDao, JobDao, etc.
            //    For example:
            //    Race r = RaceDao.create(conn, "Human", "description...");
            //    ...

            // 9) Optionally test delete
            // PlayerDao.delete(conn, fetchedP1);
            // System.out.println("Deleted Player with ID=" + fetchedP1.getPlayerID());

            // all done
            System.out.println("\n===== ALL DAO TESTS COMPLETED SUCCESSFULLY =====");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Drop and create the specified database using a schemaless connection.
     */
    private static void dropAndCreateDatabase(Connection schemalessConn, String dbName) 
            throws SQLException {
        try (Statement st = schemalessConn.createStatement()) {
            st.executeUpdate("DROP DATABASE IF EXISTS " + dbName);
            st.executeUpdate("CREATE DATABASE " + dbName);
        }
    }

    /**
     * Create all the tables you need for Milestone 3.
     * This is just an example for Player, StatType, Statistic.
     */
    private static void createTables(Connection conn) throws SQLException {
        String createPlayerTable =
            "CREATE TABLE IF NOT EXISTS Player (" +
            "  playerID INT PRIMARY KEY AUTO_INCREMENT," +
            "  username VARCHAR(255) NOT NULL," +
            "  email VARCHAR(255) NOT NULL," +
            "  serverRegion VARCHAR(50)," +
            "  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";

        String createStatTypeTable =
            "CREATE TABLE IF NOT EXISTS StatType (" +
            "  statTypeID INT PRIMARY KEY AUTO_INCREMENT," +
            "  name VARCHAR(255) NOT NULL," +
            "  description TEXT" +
            ")";

        String createStatisticTable =
            "CREATE TABLE IF NOT EXISTS Statistic (" +
            "  statisticID INT PRIMARY KEY AUTO_INCREMENT," +
            "  statValue INT NOT NULL," +
            "  statTypeID INT NOT NULL," +
            "  FOREIGN KEY (statTypeID) REFERENCES StatType(statTypeID)" +
            ")";

        try (Statement st = conn.createStatement()) {
            st.executeUpdate(createPlayerTable);
            st.executeUpdate(createStatTypeTable);
            st.executeUpdate(createStatisticTable);
            // You can add more CREATE TABLE statements for other tables:
            // Race, Job, Gear, etc.
        }
    }
}
