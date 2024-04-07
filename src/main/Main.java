package main;
import controller.TillController;
import model.Transaction;
import view.TillView;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactions = TillController.readTransactionsFromFile("input.txt"); // Use the static method

        TillView view = new TillView();

        TillController controller = new TillController(view);
        controller.processTransactions(transactions);

        try {
            view.saveToFile("receipt.txt"); // Save receipt to a file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
