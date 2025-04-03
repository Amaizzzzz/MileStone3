package cs5200project.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs5200project.model.Job;

public class JobDao {
    private JobDao() {
    }

    public static Job create(Connection cxn, String jobName) throws SQLException {
        final String insertJob = """
            INSERT INTO Job(jobName)
            VALUES (?);
        """;

        try (PreparedStatement insertStmt = cxn.prepareStatement(insertJob,
                Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, jobName);
            insertStmt.executeUpdate();

            return new Job(Utils.getAutoIncrementKey(insertStmt), jobName);
        }
    }

	public static Job getJobById(Connection cxn, int jobId)
			throws SQLException {
        final String selectJob = """
            SELECT * FROM Job 
              WHERE jobID = ?;
        """;
        try (PreparedStatement selectStmt = cxn.prepareStatement(selectJob)) {
			selectStmt.setInt(1, jobId);
            try (ResultSet results = selectStmt.executeQuery()) {
                if (results.next()) {
                    String jobName = results.getString("jobName");
					return new Job(jobId, jobName);
                } else {
                    return null;
                }
            }
        }
    }

    public static void delete(Connection cxn, Job job) throws SQLException {
        final String delete = """
            DELETE FROM Job 
            WHERE jobID = ?;
        """;
        try (PreparedStatement stmt = cxn.prepareStatement(delete)) {
            stmt.setInt(1, job.getJobID());
            stmt.executeUpdate();   
        }
    }
}
