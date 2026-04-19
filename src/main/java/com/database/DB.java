package com.database;

import com.exceptions.DBExceptionHandler;
import com.model.ApplicationStatus;
import com.model.JobApplication;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    static Dotenv dotenv = Dotenv.load();

    static String url =dotenv.get("DB_URL");
    static String user =dotenv.get("DB_USERNAME");
    static String password =dotenv.get("DB_PASSWORD");

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
    }

    public void addApplication(JobApplication jobApplication) {
        String statement = "INSERT INTO job_applications (id, application_name, job_name, job_description, status, date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(statement)) {
            preparedStatement.setLong(1, jobApplication.getId());
            preparedStatement.setString(2, jobApplication.getApplicationName());
            preparedStatement.setString(3, jobApplication.getJobName());
            preparedStatement.setString(4, jobApplication.getJobDescription());
            preparedStatement.setString(5, jobApplication.getStatus().name());
            preparedStatement.setObject(6, jobApplication.getDate());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
    }

    public void deleteById(Long id) {
        String statement = "DELETE FROM job_applications WHERE id = ?";
        try (PreparedStatement statement1 = getConnection().prepareStatement(statement)) {
            statement1.setLong(1, id);
            int rowsAffected = statement1.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        } catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
    }

    public List<JobApplication> getAllApplications() {
        String statement = "SELECT * FROM job_applications";
        List<JobApplication> jobApplications = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(statement);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                JobApplication jobApplication = new JobApplication(
                        resultSet.getLong("id"),
                        resultSet.getString("application_name"),
                        resultSet.getString("job_name"),
                        resultSet.getString("job_description"),
                        ApplicationStatus.valueOf(resultSet.getString("status")),
                        resultSet.getDate("date").toLocalDate()
                );
                jobApplications.add(jobApplication);
            }
        } catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
        return jobApplications;
    }

    public JobApplication getApplicationById(Long id) {
        String statement = "SELECT * FROM job_applications WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(statement)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new JobApplication(
                            resultSet.getLong("id"),
                            resultSet.getString("application_name"),
                            resultSet.getString("job_name"),
                            resultSet.getString("job_description"),
                            ApplicationStatus.valueOf(resultSet.getString("status")),
                            resultSet.getDate("date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
        return null;
    }

    public void updateApplication(JobApplication jobApplication) {
        String statement = "UPDATE job_applications SET application_name = ?, job_name = ?, job_description = ?, status = ?, date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, jobApplication.getApplicationName());
            preparedStatement.setString(2, jobApplication.getJobName());
            preparedStatement.setString(3, jobApplication.getJobDescription());
            preparedStatement.setString(4, jobApplication.getStatus().name());
            preparedStatement.setObject(5, jobApplication.getDate());
            preparedStatement.setLong(6, jobApplication.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
    }
    public void deleteAllApplications(){
        String statement = "DELETE FROM job_applications";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(statement)){
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }catch (SQLException e) {
            throw new DBExceptionHandler().Handle(e);
        }
    }
    public void deleteApplicationByStatus(ApplicationStatus applicationStatus) {
        String statement = "DELETE FROM job_applications WHERE status = ?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, applicationStatus.name());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }catch (SQLException e){
            throw new DBExceptionHandler().Handle(e);
        }
    }
}
