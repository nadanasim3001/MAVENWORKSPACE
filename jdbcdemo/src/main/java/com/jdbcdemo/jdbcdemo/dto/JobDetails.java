package com.jdbcdemo.jdbcdemo.dto;

public class JobDetails {
	
	String jobId="";
	
	Double maxSalary = 0D;
	Double minSalary=0D;
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Double getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(Double minSalary) {
		this.minSalary = minSalary;
	}
	public Double getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(Double maxSalary) {
		this.maxSalary = maxSalary;
	}
	@Override
	public String toString() {
		return "JobDetails [jobId=" + jobId + ", maxSalary=" + maxSalary + ", minSalary=" + minSalary + "]";
	}

	

}
