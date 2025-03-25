package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.GearJob;
import cs5200project.model.Item;
import cs5200project.model.Job;

public class GearJobDao {
    private GearJobDao() {
    }

    public static boolean create(Connection cxn, int itemId, int jobId) throws SQLException {
        final String insertGearJob = """
            INSERT INTO GearJob(itemID, jobID)
            VALUES (?, ?);
        """;

        try (PreparedStatement insertStmt = cxn.prepareStatement(insertGearJob)) {
            insertStmt.setInt(1, itemId);
            insertStmt.setInt(2, jobId);
            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static GearJob getGearJobByIds(Connection cxn, int itemId, int jobId) throws SQLException {
        final String selectGearJob = """
            SELECT i.*, j.*
            FROM GearJob gj
            JOIN Item i ON gj.itemID = i.itemID
            JOIN Job j ON gj.jobID = j.jobID
            WHERE gj.itemID = ? AND gj.jobID = ?;
        """;
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
            selectStmt.setInt(1, itemId);
            selectStmt.setInt(2, jobId);
            try (ResultSet results = selectStmt.executeQuery()) {
                if (results.next()) {
                    Item item = new Item(
                        results.getInt("itemID"),
                        results.getString("itemName"),
                        results.getInt("itemLevel"),
                        results.getInt("maxStackSize"),
                        results.getDouble("price"),
                        results.getInt("quantity")
                    );
                    Job job = new Job(
                        results.getInt("jobID"),
                        results.getString("jobName"),
                        results.getString("jobDescription")
                    );
                    return new GearJob(item, job);
                } else {
                    return null;
                }
            }
        }
    }

    public static List<GearJob> getGearJobsByItemId(Connection cxn, int itemId) throws SQLException {
        final String selectGearJob = """
            SELECT i.*, j.*
            FROM GearJob gj
            JOIN Item i ON gj.itemID = i.itemID
            JOIN Job j ON gj.jobID = j.jobID
            WHERE gj.itemID = ?;
        """;
        List<GearJob> gearJobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
            selectStmt.setInt(1, itemId);
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
                    Item item = new Item(
                        results.getInt("itemID"),
                        results.getString("itemName"),
                        results.getInt("itemLevel"),
                        results.getInt("maxStackSize"),
                        results.getDouble("price"),
                        results.getInt("quantity")
                    );
                    Job job = new Job(
                        results.getInt("jobID"),
                        results.getString("jobName"),
                        results.getString("jobDescription")
                    );
                    gearJobList.add(new GearJob(item, job));
                }
                return gearJobList;
            }
        }
    }

    public static List<GearJob> getGearJobsByJobId(Connection cxn, int jobId) throws SQLException {
        final String selectGearJob = """
            SELECT i.*, j.*
            FROM GearJob gj
            JOIN Item i ON gj.itemID = i.itemID
            JOIN Job j ON gj.jobID = j.jobID
            WHERE gj.jobID = ?;
        """;
        List<GearJob> gearJobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
            selectStmt.setInt(1, jobId);
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
                    Item item = new Item(
                        results.getInt("itemID"),
                        results.getString("itemName"),
                        results.getInt("itemLevel"),
                        results.getInt("maxStackSize"),
                        results.getDouble("price"),
                        results.getInt("quantity")
                    );
                    Job job = new Job(
                        results.getInt("jobID"),
                        results.getString("jobName"),
                        results.getString("jobDescription")
                    );
                    gearJobList.add(new GearJob(item, job));
                }
                return gearJobList;
            }
        }
    }

    public static void delete(Connection cxn, int itemId, int jobId) throws SQLException {
        final String delete = "DELETE FROM GearJob WHERE itemID = ? AND jobID = ?;";
        try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, jobId);
            stmt.executeUpdate();
        }
    }
}
