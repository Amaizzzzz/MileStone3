package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Gear;
import cs5200project.model.GearJob;
import cs5200project.model.Job;

public class GearJobDao {
    private GearJobDao() {
    }

    public static GearJob create(Connection cxn, Gear gearItem, Job job) throws SQLException {
        return create(cxn, gearItem.getItemId(), job.getJobID());
    }

    public static GearJob create(Connection cxn, int itemID, int jobID) throws SQLException {
        final String insertGearJob = """
            INSERT INTO GearJob(itemID, jobID)
            VALUES (?, ?);
        """;

        try (PreparedStatement insertStmt = cxn.prepareStatement(insertGearJob)) {
            insertStmt.setInt(1, itemID);
            insertStmt.setInt(2, jobID);
            insertStmt.executeUpdate();

            Gear gear = GearDao.getGearByItemID(cxn, itemID);
            Job job = JobDao.getJobById(cxn, jobID);
            
            return new GearJob(gear, job);
        }
    }

    public static GearJob getGearJobByItemIdAndJobId(Connection cxn, Gear gearItem, Job job) throws SQLException {
        final String selectGearJob = """
            SELECT *
            FROM GearJob
            WHERE itemID = ? AND jobID = ?;
        """;
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
            selectStmt.setInt(1, gearItem.getItemId());
            selectStmt.setInt(2, job.getJobID());

            try (ResultSet results = selectStmt.executeQuery()) {
                if (results.next()) {
					Gear gearRs = GearDao.getGearByItemID(cxn,
							results.getInt("itemID"));
					Job jobRs = JobDao.getJobById(cxn, job.getJobID());
					return new GearJob(gearRs, jobRs);
                } else {
                    return null;
                }
            }
        }
    }

    public static List<GearJob> getGearJobsByItemId(Connection cxn, Gear gearItem) throws SQLException {
        final String selectGearJob = """
            SELECT *
            FROM GearJob
            WHERE itemID = ?;
        """;
        List<GearJob> gearJobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
			selectStmt.setInt(1, gearItem.getItemId());
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
					Gear gear = GearDao.getGearByItemID(cxn,
							results.getInt("itemID"));
                    Job job = JobDao.getJobById(cxn, results.getInt("jobID"));
                    gearJobList.add(new GearJob(gear, job));
                }
                return gearJobList;
            }
        }
    }

    public static List<GearJob> getGearJobsByJobId(Connection cxn, int jobId) throws SQLException {
        final String selectGearJob = """
            SELECT *
            FROM GearJob
            WHERE jobID = ?;
        """;
        List<GearJob> gearJobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectGearJob)) {
            selectStmt.setInt(1, jobId);
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
					Gear gear = GearDao.getGearByItemID(cxn,
							results.getInt("itemID"));
                    Job job = JobDao.getJobById(cxn, results.getInt("jobID"));
                    gearJobList.add(new GearJob(gear, job));
                }
                return gearJobList;
            }
        }
    }

    public static void delete(Connection cxn, int itemId, int jobId) throws SQLException {
        final String delete = """
            DELETE FROM GearJob 
            WHERE itemID = ? AND jobID = ?;
        """;
        try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
			stmt.setInt(1, itemId);
			stmt.setInt(2, jobId);
            stmt.executeUpdate();
        }
    }
}
