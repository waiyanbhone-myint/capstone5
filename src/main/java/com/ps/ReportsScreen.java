package com.ps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReportsScreen {

    private boolean backToHome = false;
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "transactions.csv";

    public ReportsScreen() {
        displayReportsMenu();
    }

    private void displayReportsMenu() {
        int option;
        do {
            System.out.println("\n--- Reports Menu ---\n");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger Screen");
            System.out.print("Enter your option: ");

            option = scanner.nextInt();
            scanner.nextLine(); // Fixed: consume newline after nextInt()

            switch (option) {
                case 1:
                    monthToDate();
                    break;
                case 2:
                    previousMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    previousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    backToHome = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!backToHome); // Fixed: use instance variable
    }

    private void monthToDate() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        System.out.println("\n--- Month To Date ---");
        printHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    try {
                        LocalDate txDate = LocalDate.parse(tx[0]);
                        if (txDate.getMonthValue() == currentMonth && txDate.getYear() == currentYear) {
                            System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                    tx[0], tx[1], tx[2], tx[3], tx[4]);
                            found = true;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing date: " + tx[0]);
                    }
                }
            }
            if (!found) {
                System.out.println("No transactions found for this month.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void previousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonthDate = today.minusMonths(1);
        int prevMonth = previousMonthDate.getMonthValue();
        int prevYear = previousMonthDate.getYear();

        System.out.println("\n--- Previous Month ---");
        printHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    try {
                        LocalDate txDate = LocalDate.parse(tx[0]);
                        if (txDate.getMonthValue() == prevMonth && txDate.getYear() == prevYear) {
                            System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                    tx[0], tx[1], tx[2], tx[3], tx[4]);
                            found = true;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing date: " + tx[0]);
                    }
                }
            }
            if (!found) {
                System.out.println("No transactions found for previous month.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void yearToDate() {
        int currentYear = LocalDate.now().getYear();

        System.out.println("\n--- Year To Date ---");
        printHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    try {
                        LocalDate txDate = LocalDate.parse(tx[0]);
                        if (txDate.getYear() == currentYear) {
                            System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                    tx[0], tx[1], tx[2], tx[3], tx[4]);
                            found = true;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing date: " + tx[0]);
                    }
                }
            }
            if (!found) {
                System.out.println("No transactions found for this year.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void previousYear() {
        LocalDate today = LocalDate.now();
        int prevYear = today.getYear() - 1; // Fixed: now gets actual previous year

        System.out.println("\n--- Previous Year ---");
        printHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    try {
                        LocalDate txDate = LocalDate.parse(tx[0]);
                        if (txDate.getYear() == prevYear) { // Fixed: only check year
                            System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                    tx[0], tx[1], tx[2], tx[3], tx[4]);
                            found = true;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing date: " + tx[0]);
                    }
                }
            }
            if (!found) {
                System.out.println("No transactions found for previous year.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void searchByVendor() {
        // Fixed: scanner.nextLine() already called in main loop
        System.out.print("Enter vendor name to search: ");
        String vendorInput = scanner.nextLine().toLowerCase();

        System.out.println("\n--- Search Results for: " + vendorInput + " ---");
        printHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5 && tx[3].toLowerCase().contains(vendorInput)) {
                    System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                            tx[0], tx[1], tx[2], tx[3], tx[4]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No transactions found for that vendor.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void printHeader() {
        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");
    }
}