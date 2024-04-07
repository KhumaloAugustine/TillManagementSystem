package main;

import controller.TillController;
import model.Transaction;
import view.TillView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactions = TillController.readTransactionsFromFile("input.txt"); // Use the static method

        TillView view = new TillView();

        TillController controller = new TillController(view);
        controller.processTransactions(transactions);
    }
}