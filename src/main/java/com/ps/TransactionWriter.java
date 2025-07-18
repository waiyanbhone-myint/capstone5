package com.ps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionWriter {

    public static void saveTransactionToFile(String description, String vendor, float amount) {
        // Append transaction to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString();

            writer.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}