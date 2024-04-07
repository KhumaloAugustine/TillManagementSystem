package controller;

import model.Change;
import model.Item;
import model.Transaction;
import view.TillView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TillController {
    private TillView view;

    public TillController(TillView view) {
        this.view = view;
    }

    public void processTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            // Process each transaction
            int tillStart = 500; // Assuming till starts with a float of R500
            int transactionTotal = calculateTransactionTotal(transaction);
            int changeTotal = calculateChangeTotal(transaction);
            Change changeGiven = calculateChange(transaction, changeTotal);
            view.displayTransactionResult(tillStart, transactionTotal, transaction.getAmountPaid(), changeTotal, changeGiven);
        }
    }

    private int calculateTransactionTotal(Transaction transaction) {
        int total = 0;
        for (Item item : transaction.getItems()) {
            total += item.getAmount();
        }
        return total;
    }

    private int calculateChangeTotal(Transaction transaction) {
        int transactionTotal = calculateTransactionTotal(transaction);
        return transaction.getAmountPaid() - transactionTotal;
    }

    private Change calculateChange(Transaction transaction, int changeTotal) {
        Map<Integer, Integer> changeBreakdown = new HashMap<>();
        int remainingChange = changeTotal;

        // Define your currency denominations here
        int[] denominations = {200, 100, 50, 20, 10, 5, 2, 1};

        for (int denomination : denominations) {
            int numNotes = remainingChange / denomination;
            if (numNotes > 0) {
                changeBreakdown.put(denomination, numNotes);
                remainingChange -= numNotes * denomination;
            }
        }

        return new Change(changeTotal, changeBreakdown);
    }

    public static List<Transaction> readTransactionsFromFile(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split by comma (items and payment)
                List<Item> items = new ArrayList<>();
                String[] itemDetails = parts[0].split(";"); // Split items by semicolon

                for (String itemDescription : itemDetails) {
                    String[] descriptionAmount = itemDescription.trim().split("\\s+", 2); // Split description and amount (optional unit)
                    if (descriptionAmount.length >= 1) {
                        String description = descriptionAmount[0];
                        int amount = parseAmount(descriptionAmount.length > 1 ? descriptionAmount[1] : ""); // Extract amount (handle missing)
                        items.add(new Item(description, amount));
                    } else {
                        System.err.println("Error: Invalid item format: " + itemDescription);
                    }
                }

                String[] paymentDetails = parts[1].split("-"); // Split payment details
                int amountPaid = 0;
                for (String payment : paymentDetails) {
                    amountPaid += parseAmount(payment.trim()); // Parse each paid amount (assuming no unit)
                }
                transactions.add(new Transaction(items, amountPaid));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static int parseAmount(String amountString) {
        if (amountString.isEmpty()) {
            return 0; // Handle missing amount (optional)
        } else {
            return Integer.parseInt(amountString.replaceAll("[^0-9]", "")); // Extract numeric part only (remove non-numeric characters)
        }
    }
}