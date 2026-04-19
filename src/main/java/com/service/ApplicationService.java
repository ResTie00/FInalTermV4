package com.service;

import com.database.DB;
import com.exceptions.DuplicateEntryException;
import com.exceptions.NotFoundException;
import com.model.JobApplication;


import java.util.List;


public class ApplicationService {
    private final DB db = new DB();

    public void createApplication(JobApplication jobApplication) {
        if (jobApplication.getApplicationName() == null || jobApplication.getApplicationName().isEmpty() || jobApplication.getJobName() == null || jobApplication.getJobDescription() == null) {
            throw new IllegalArgumentException("Application name, job name, and job description cannot be null.");
        }
        if (db.getApplicationById(jobApplication.getId()) != null) {
            throw new DuplicateEntryException("Application with this id already exists.");
        }
        db.addApplication(jobApplication);
    }

    public JobApplication getApplicationById(Long id) {
        JobApplication jobApplication = db.getApplicationById(id);
        if (jobApplication == null) {
            throw new NotFoundException("Application with id " + id + " not found.");
        }
        return jobApplication;
    }

    public List<JobApplication> getAllApplications() {
        return db.getAllApplications();
    }

    public void deleteApplicationById(Long id) {
        JobApplication jobApplication = db.getApplicationById(id);
        if (jobApplication == null) {
            throw new NotFoundException("Application with id " + id + " not found.");
        }
        db.deleteById(id);
    }

    public void deleteAllApplications() {
        db.deleteAllApplications();
    }

    public void updateApplication(JobApplication jobApplication) {
        JobApplication existingApplication = db.getApplicationById(jobApplication.getId());
        if (existingApplication == null) {
            throw new NotFoundException("Application with id " + jobApplication.getId() + " not found.");
        }
        db.updateApplication(jobApplication);
    }
}