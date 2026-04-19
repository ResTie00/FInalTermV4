package com;


import com.exceptions.ConnectionFailedException;
import com.exceptions.DuplicateEntryException;
import com.model.ApplicationStatus;
import com.model.JobApplication;
import com.service.ApplicationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ApplicationService service = new ApplicationService();
    public static void main(String[] args) {
        while (true){
            System.out.println("1. Add application\n2. Delete application\n3. Get all applications\n4. Get application by id\n5. Update application \n6. Delete all applications\n7. Exit");
            int choice;
            while(true) {
                String input = scanner.nextLine();

                if (!isInt(input)) {
                    System.out.println("Invalid input. Enter a number.");
                    continue;
                }
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice. Choose between 1-7.");
                    continue;
                }

                break;
            }

             switch (choice){
                 case 1:
                        JobApplication jobApplication = ApplicationMaker();
                     try {
                         service.createApplication(jobApplication);
                     } catch (DuplicateEntryException e) {
                         System.out.println("Already exist: " + e.getMessage());
                     } catch (ConnectionFailedException e) {
                         System.out.println("No connection established");
                     } catch (RuntimeException e) {
                         System.out.println("General error: " + e.getMessage());
                     }
                        break;
                 case 2:
                     System.out.println("Enter id");
                     long id1;
                     while (true) {
                         String input = scanner.nextLine();

                         if (!isLong(input)) {
                             System.out.println("Invalid input. Enter a number.");
                             continue;
                         }
                         id1 = Long.parseLong(input);
                         break;
                     }
                     service.deleteApplicationById(id1);
                     break;
                 case 3:
                     System.out.println("---------------------------------------------------");
                     service.getAllApplications().forEach(System.out::println);
                     System.out.println("---------------------------------------------------");
                     System.out.println(" ");
                     break;
                 case 4:
                        System.out.println("Enter id");
                        long id2;
                        while (true) {
                            String input = scanner.nextLine();

                            if (!isLong(input)) {
                                System.out.println("Invalid input. Enter a number.");
                                continue;
                            }
                            id2 = Long.parseLong(input);
                            break;
                        }
                        System.out.println("---------------------------------------------------");
                        System.out.println(service.getApplicationById(id2));
                        System.out.println("---------------------------------------------------");
                        System.out.println(" ");
                        break;
                 case 5:
                     JobApplication jobApplication1 = ApplicationMaker();
                     service.updateApplication(jobApplication1);
                     break;
                 case 6:
                        int choice1;
                        while (true) {
                            System.out.println("Are you sure you want to delete all applications? (1 - Yes, 2 - No): ");
                            String input = scanner.nextLine();
                            if (!isInt(input)) {
                                System.out.println("Invalid input. Enter a number.");
                                continue;
                            }
                            if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 2) {
                                System.out.println("Invalid choice. Choose between 1-2.");
                                continue;
                            }
                            choice1 = Integer.parseInt(input);
                            break;
                        }
                        if (choice1 == 2){
                            break;
                        }
                        service.deleteAllApplications();
                        break;
                 case 7:
                     System.out.println("EXITING...");
                     return;
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
        }
    }
    static JobApplication ApplicationMaker(){
        long id;
        while (true){
            System.out.println("Enter id: ");
            String input = scanner.nextLine();

            if (!isLong(input)) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            id = Long.parseLong(input);
            break;
        }
        System.out.println("Enter application name: ");
        String applicationName  = scanner.next();
        System.out.println("Enter job name: ");
        scanner.nextLine();
        String jobName  = scanner.next();
        System.out.println("Enter job description: ");
        scanner.nextLine();
        String jobDescription = scanner.nextLine();
        ApplicationStatus status = ApplicationStatus.UNKNOWN;
        int statusChoice;

        while (true) {
            System.out.println("Enter status: " +
                    "(1 - REJECTED,\n" +
                    "(2 - PENDING,\n" +
                    "(3 - PAUSED,\n" +
                    "(4 - APPROVED): ");

            String input = scanner.nextLine();

            if (!isInt(input)) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            statusChoice = Integer.parseInt(input);

            if (statusChoice < 1 || statusChoice > 4) {
                System.out.println("Invalid choice. Choose between 1-4.");
                continue;
            }

            break;
        }
        switch (statusChoice){
            case 1:
                status = ApplicationStatus.REJECTED;
                break;
            case 2:
                status = ApplicationStatus.PENDING;
                break;
            case 3:
                status = ApplicationStatus.PAUSED;
                break;
            case 4:
                status = ApplicationStatus.APPROVED;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        LocalDate date2;
        while (true) {
            System.out.println("Enter date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            try {
                date2 = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
        return new JobApplication(id, applicationName, jobName, jobDescription, status, date2 );
    }
    static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}