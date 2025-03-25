package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs5200project.model.Job;

public class JobDao {
    private JobDao() {
    }

    public static int create(Connection cxn, String jobName) throws SQLException {
        final String insertJob = """
            INSERT INTO Job(jobName)
            VALUES (?);
        """;

        try (PreparedStatement insertStmt = cxn.prepareStatement(insertJob,
                Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, jobName);
            insertStmt.executeUpdate();

            return Utils.getAutoIncrementKey(insertStmt);
        }
    }

    public static Job getJobById(Connection cxn, int jobId) throws SQLException {
        final String selectJob = "SELECT * FROM Job WHERE jobID = ?;";
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectJob)) {
            selectStmt.setInt(1, jobId);
            try (ResultSet results = selectStmt.executeQuery()) {
                if (results.next()) {
                    return new Job(
                        results.getInt("jobID"),
                        results.getString("jobName")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public static List<Job> getJobsByPartialName(Connection cxn, String partialName) throws SQLException {
        final String selectJob = "SELECT * FROM Job WHERE jobName LIKE ?;";
        List<Job> jobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectJob)) {
            selectStmt.setString(1, "%" + partialName + "%");
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
                    jobList.add(new Job(
                        results.getInt("jobID"),
                        results.getString("jobName")
                    ));
                }
                return jobList;
            }
        }
    }

    public static List<Job> getAllJobs(Connection cxn) throws SQLException {
        final String selectJob = "SELECT * FROM Job ORDER BY jobID;";
        List<Job> jobList = new ArrayList<>();
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectJob)) {
            try (ResultSet results = selectStmt.executeQuery()) {
                while (results.next()) {
                    jobList.add(new Job(
                        results.getInt("jobID"),
                        results.getString("jobName")
                    ));
                }
                return jobList;
            }
        }
    }

    public static <T extends Job> T updateJobName(Connection cxn, T job, String newJobName) throws SQLException {
        final String update = "UPDATE Job SET jobName = ? WHERE jobID = ?;";
        try (PreparedStatement updateStmt = cxn.prepareStatement(update)) {
            updateStmt.setString(1, newJobName);
            updateStmt.setInt(2, job.getJobID());
            updateStmt.executeUpdate();

            job.setJobName(newJobName);
            return job;
        }
    }

    public static void delete(Connection cxn, Job job) throws SQLException {
        final String delete = "DELETE FROM Job WHERE jobID = ?;";
        try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
            stmt.setInt(1, job.getJobID());
            stmt.executeUpdate();
        }
    }
}
