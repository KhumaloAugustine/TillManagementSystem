package main;

import controller.TillController;
import model.Transaction;
import view.TillView;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Read transactions from a file
        List<Transaction> transactions = TillController.readTransactionsFromFile("input.txt"); // Use the static method

        // Create a TillView object for displaying output
        TillView view = new TillView();

        // Create a TillController object and process transactions
        TillController controller = new TillController(view);
        controller.processTransactions(transactions);

        // (Optional) Write the output to a file (you already have the writeOutputToFile method in view)
    }
}