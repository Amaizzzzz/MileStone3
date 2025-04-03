package cs5200project.model;

import java.util.Objects;

public class Job {
    private int jobID;         // Primary key
    private String jobName;

    // constructor
    public Job(int jobID, String jobName) {
        this.jobID = jobID;
        this.jobName = jobName;
    }

    // Getter of jobID
    public int getJobID() {
        return jobID;
    }

    // Setter of jobID
    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    // Getter of jobName
    public String getJobName() {
        return jobName;
    }

    // Setter of jobName
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(jobID, jobName);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Job job = (Job) obj;
        return jobID == job.jobID && Objects.equals(jobName, job.jobName);
    }

    // toString method
    @Override
    public String toString() {
        return String.format("Job[%d, %s]", jobID, jobName);
    }

}
