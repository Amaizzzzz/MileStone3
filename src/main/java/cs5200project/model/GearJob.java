package cs5200project.model;

import java.util.Objects;

public class GearJob {
    private Item item;
    private Job job;

    // constructor
    public GearJob(Item item, Job job) {
        this.item = item;
        this.job = job;
    }

    // Getter of item
    public Item getItem() {
        return item;
    }

    // Setter of item
    public void setItem(Item item) {
        this.item = item;
    }

    // Getter of job
    public Job getJob() {
        return job;
    }

    // Setter of job
    public void setJob(Job job) {
        this.job = job;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(item, job);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GearJob gearJob = (GearJob) obj;
        return Objects.equals(item, gearJob.item) && Objects.equals(job, gearJob.job);
    }

    // toString method
    @Override
    public String toString() {
		return String.format("GearJob[%s, %s]", item.getItemId(),
				job.getJobID());
    }
}
    
    
    

