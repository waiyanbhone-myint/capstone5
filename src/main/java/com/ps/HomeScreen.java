package com.ps;

import java.util.Scanner;

public class HomeScreen {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("---\nWelcome to the General Ledger!\n---");
            System.out.println("1) Add Deposit");
            System.out.println("2) Make Payment");
            System.out.println("3) Go to Ledger");
            System.out.println("4) Banking Assistant");
            System.out.println("0) Exit");
            System.out.print("Please enter the option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    ledgerScreen();
                    break;
                case 4:
                    bankingAssistant();
                    break;
                case 0:
                    System.out.println("Have a good one!");
                    break;
                default:
                    System.out.println("Invalid. Please try again.");
            }

        } while (option != 0);

        scanner.close();
    }

    private static void addDeposit() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        float amount = 0;
        while (true) {
            System.out.print("Enter deposit amount: ");
            String input = scanner.nextLine();
            try {
                amount = Float.parseFloat(input);
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Use TransactionWriter instead of saveTransactionToFile
        TransactionWriter.saveTransactionToFile(description, vendor, amount);
        System.out.println("Deposit successful!");
        System.out.println("Thank you!");
    }

    private static void makePayment() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        float amount = 0;
        while (true) {
            System.out.print("Enter payment amount: ");
            String input = scanner.nextLine();
            try {
                amount = Float.parseFloat(input);
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Use TransactionWriter instead of saveTransactionToFile (negative for payment)
        TransactionWriter.saveTransactionToFile(description, vendor, -amount);
        System.out.println("Payment successful!");
        System.out.println("Thank you!");
    }

    public static void ledgerScreen() {
        LedgerScreen ledgerScreenInstance = new LedgerScreen();
    }

    private static void bankingAssistant() {
        Assistant assistant = new Assistant();
    }
}