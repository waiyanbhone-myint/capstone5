package com.ps;

import java.text.NumberFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LedgerScreen {

    private String fileName = "transactions.csv";
    private boolean backToHome = false;
    private Scanner scanner = new Scanner(System.in);
    private List<Transaction> transactions = new ArrayList<>();

    public LedgerScreen() {
        loadTransactionsFromFile();
        displayLedgerMenu();

    }

    private void loadTransactionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    try {
                        String date = parts[0].trim();
                        String time = parts[1].trim();
                        String description = parts[2].trim();
                        String vendor = parts[3].trim();

                        NumberFormat format = NumberFormat.getInstance(Locale.US);
                        Number number = format.parse(parts[4].trim());
                        float amount = number.floatValue();
                        transactions.add(new Transaction(date, time, description, vendor, amount));
                    } catch (Exception e) {
                        System.err.println("Error parsing amount in line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid format in line (expected 5 fields): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName + " - " + e.getMessage());
            System.out.println("Please ensure the file exists and is accessible.");

        }
    }

    private void displayLedgerMenu() {
        int option;

        do {
            System.out.println("\n--- Ledger Menu ---\n");
            System.out.println("1) All Transactions");
            System.out.println("2) Deposits");
            System.out.println("3) Payments");
            System.out.println("4) Reports");
            System.out.println("0) Back to Home Screen");
            System.out.println("Enter your option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    showAllLedgers();
                    break;
                case 2:
                    showFilteredLedgers(true);
                    break;
                case 3:
                    showFilteredLedgers(false);
                    break;
                case 4:
                    showReports();
                    break;
                case 0:
                    backToHome = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!backToHome);
    }

    private void showAllLedgers() {
        System.out.println("\n--- All Transactions ---\n");
        printTransactionHeader();

        if (transactions.isEmpty()) {
            System.out.println("No transactions loaded.");
        } else {
            for (Transaction tx : transactions) {
                System.out.println(tx);
            }
        }
    }

    private void showFilteredLedgers(boolean isDeposit) {
        System.out.println("\n--- " + (isDeposit ? "Deposits" : "Payments") + " ---\n");
        printTransactionHeader();
        boolean found = false;
        for (Transaction tx : transactions) {
            if (isDeposit && tx.getAmount() > 0) {
                System.out.println(tx);
                found = true;
            } else if (!isDeposit && tx.getAmount() < 0) {
                System.out.println(tx);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No " + (isDeposit ? "deposits" : "payments") + " found.");
        }
    }

    public static void showReports() {
        ReportsScreen reportsScreenInstance = new ReportsScreen();
    }

    private void printTransactionHeader() {
        System.out.printf("%-12s %-10s %-25s %-20s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("------------------------------------------------------------------------------------");

    }
    public static class Transaction {
        private String date;
        private String time;
        private String description;
        private String vendor;
        private float amount;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public Transaction(String date, String time, String description, String vendor, float amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("%-12s %-10s %-30s %-25s %10.2f",
                    date, time, description, vendor, amount);
        }

    }
}