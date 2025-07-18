package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Assistant {

    private Scanner scanner = new Scanner(System.in);
    private String fileName = "transaction.csv";

    public Assistant() {
        showMenu();
    }

    private void showMenu() {
        int option;

        do {
            System.out.println("\n--- Banking Assistant ---");
            System.out.println("1) Show all my transactions");
            System.out.println("2) How much did I spend total?");
            System.out.println("3) How much money did I earn?");
            System.out.println("4) Find transactions with a vendor");
            System.out.println("5) Show my biggest expense");
            System.out.println("0) Go back");
            System.out.print("Choose option: ");

            option = scanner.nextInt();
            scanner.nextLine(); // clear the line

            if (option == 1) {
                showAllTransactions();
            } else if (option == 2) {
                showTotalSpent();
            } else if (option == 3) {
                showTotalEarned();
            } else if (option == 4) {
                findVendor();
            } else if (option == 5) {
                showBiggestExpense();
            } else if (option == 0) {
                System.out.println("Going back...");
            } else {
                System.out.println("Invalid choice. Try again.");
            }

        } while (option != 0);
    }

    private void showAllTransactions() {
        System.out.println("\n--- All Your Transactions ---");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String date = parts[0];
                    String description = parts[2];
                    String vendor = parts[3];
                    String amount = parts[4];

                    System.out.println(date + " | " + vendor + " | $" + amount + " | " + description);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }

    private void showTotalSpent() {
        System.out.println("\n--- Total Money Spent ---");

        float totalSpent = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    float amount = Float.parseFloat(parts[4]);

                    if (amount < 0) { // negative means you spent money
                        totalSpent = totalSpent + Math.abs(amount); // make it positive
                    }
                }
            }
            reader.close();

            System.out.println("You spent: $" + totalSpent);

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }

    private void showTotalEarned() {
        System.out.println("\n--- Total Money Earned ---");

        float totalEarned = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    float amount = Float.parseFloat(parts[4]);

                    if (amount > 0) { // positive means you earned money
                        totalEarned = totalEarned + amount;
                    }
                }
            }
            reader.close();

            System.out.println("You earned: $" + totalEarned);

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }

    private void findVendor() {
        System.out.print("Enter vendor name to search: ");
        String searchVendor = scanner.nextLine();

        System.out.println("\n--- Transactions with " + searchVendor + " ---");

        boolean found = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String vendor = parts[3];

                    if (vendor.toLowerCase().contains(searchVendor.toLowerCase())) {
                        String date = parts[0];
                        String description = parts[2];
                        String amount = parts[4];

                        System.out.println(date + " | " + vendor + " | $" + amount + " | " + description);
                        found = true;
                    }
                }
            }
            reader.close();

            if (!found) {
                System.out.println("No transactions found with that vendor.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }

    private void showBiggestExpense() {
        System.out.println("\n--- Your Biggest Expense ---");

        float biggestAmount = 0;
        String biggestDate = "";
        String biggestVendor = "";
        String biggestDescription = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    float amount = Float.parseFloat(parts[4]);

                    if (amount < 0) { // negative means expense
                        float positiveAmount = Math.abs(amount);

                        if (positiveAmount > biggestAmount) {
                            biggestAmount = positiveAmount;
                            biggestDate = parts[0];
                            biggestVendor = parts[3];
                            biggestDescription = parts[2];
                        }
                    }
                }
            }
            reader.close();

            if (biggestAmount > 0) {
                System.out.println("Biggest expense: $" + biggestAmount);
                System.out.println("Date: " + biggestDate);
                System.out.println("Vendor: " + biggestVendor);
                System.out.println("Description: " + biggestDescription);
            } else {
                System.out.println("No expenses found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }
}