package com.model;


import java.time.LocalDate;


public class JobApplication extends BaseEntity implements Printable {
    private Long id;
    private String applicationName;
    private String jobName;
    private String jobDescription;
    private ApplicationStatus status;
    private LocalDate Date;

    public JobApplication(Long id, String applicationName, String jobName, String jobDescription, ApplicationStatus status, LocalDate date) {
        this.id = id;
        this.applicationName = applicationName;
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.status = status;
        Date = date;
    }
    public Long getId() {return id;}
    public String getApplicationName() {return applicationName;}
    public String getJobName() {return jobName;}
    public String getJobDescription() {return jobDescription;}
    public ApplicationStatus getStatus() {return status;}
    public LocalDate getDate() {return Date;}

    @Override
    public void print() {
        System.out.println("Job Application Details:");
        System.out.println("ID: " + id);
        System.out.println("Application Name: " + applicationName);
        System.out.println("Job Name: " + jobName);
        System.out.println("Job Description: " + jobDescription);
        System.out.println("Status: " + status);
        System.out.println("Date: " + Date);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", applicationName='" + applicationName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", status=" + status +
                ", Date=" + Date +
                '}';
    }

}
