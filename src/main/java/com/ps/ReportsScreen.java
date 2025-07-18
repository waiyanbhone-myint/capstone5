package com.ps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;


public class ReportsScreen {
    public static void main(String[] args) {
    }
    static boolean backToHome = false;
    static Scanner scanner = new Scanner(System.in);
    static String fileName = "transactions.csv";

    public ReportsScreen() {
        int option;
        do {
            System.out.println("\n--- Reports Menu ---\n");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger Screen");
            System.out.println("H) Home");
            System.out.print("Enter your option: ");

            option = scanner.nextInt();
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
        } while (option != 0);


    }
    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    LocalDate txDate = LocalDate.parse(tx[0]);
                    if (txDate.getMonthValue() == currentMonth && txDate.getYear() == currentYear) {
                        System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                tx[0], tx[1], tx[2], tx[3], tx[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }
    private static void searchByVendor() {
        scanner.nextLine(); // Consume leftover newline
        System.out.print("Enter vendor name to search: ");
        String vendorInput = scanner.nextLine().toLowerCase();

        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");

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

    private static void previousYear() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonthDate = today.minusMonths(1);
        int prevMonth = previousMonthDate.getMonthValue();
        int prevYear = previousMonthDate.getYear();

        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    LocalDate txDate = LocalDate.parse(tx[0]);
                    if (txDate.getMonthValue() == prevMonth && txDate.getYear() == prevYear) {
                        System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                tx[0], tx[1], tx[2], tx[3], tx[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void yearToDate() {
        int currentYear = LocalDate.now().getYear();

        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    LocalDate txDate = LocalDate.parse(tx[0]);
                    if (txDate.getYear() == currentYear) {
                        System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                tx[0], tx[1], tx[2], tx[3], tx[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void previousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonthDate = today.minusMonths(1);
        int prevMonth = previousMonthDate.getMonthValue();
        int prevYear = previousMonthDate.getYear();

        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("--------------------------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tx = line.split("\\|");
                if (tx.length >= 5) {
                    LocalDate txDate = LocalDate.parse(tx[0]);
                    if (txDate.getMonthValue() == prevMonth && txDate.getYear() == prevYear) {
                        System.out.printf("%-12s %-10s %-25s %-20s %10s%n",
                                tx[0], tx[1], tx[2], tx[3], tx[4]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }







    }


}

