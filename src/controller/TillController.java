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
    private int initialTillAmount;
    private Map<Integer, Integer> tillCash;

    public TillController(TillView view, int initialTillAmount) {
        this.view = view;
        this.initialTillAmount = initialTillAmount;
        this.tillCash = new HashMap<>();
        initializeTillCash(); // Optional: pre-fill denominations (see point 4)
    }

    private void initializeTillCash() {
        // Add initial quantities of each denomination (adjust values as needed)
        tillCash.put(50, 5);  // 5 x R50
        tillCash.put(20, 5);  // 5 x R20
        tillCash.put(10, 6);  // 6 x R10
        tillCash.put(5, 12);  // 12 x R5
        tillCash.put(2, 10);  // 10 x R2
        tillCash.put(1, 10);  // 10 x R1
    }

    public void processTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            int transactionTotal = calculateTransactionTotal(transaction);
            int changeTotal = calculateChangeTotal(transaction);
            Change changeGiven = calculateChange(transaction, changeTotal);

            // Update till amount based on transaction
            int customerPayment = transaction.getAmountPaid();
            int totalTillAmount = initialTillAmount + customerPayment;
            initialTillAmount = totalTillAmount; // Update initialTillAmount for subsequent transactions

            // Update denomination counts after providing change
            updateTillCash(changeGiven.getChangeBreakdown());

            view.displayTransactionResult(initialTillAmount, transactionTotal, customerPayment, changeTotal, changeGiven, totalTillAmount);
        }
    }

    private void updateTillCash(Map<Integer, Integer> changeBreakdown) {
        for (Map.Entry<Integer, Integer> entry : changeBreakdown.entrySet()) {
            int denomination = entry.getKey();
            int changeQuantity = entry.getValue();

            // Decrement used denominations, handle potential underflow (insufficient change)
            tillCash.put(denomination, tillCash.getOrDefault(denomination, 0) - changeQuantity);
            if (tillCash.get(denomination) < 0) {
                // Handle insufficient change scenario (log error, inform user, etc.)
                System.err.println("Insufficient change for denomination: " + denomination);
            }
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

        return new Change(changeTotal, changeBreakdown, transaction);
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