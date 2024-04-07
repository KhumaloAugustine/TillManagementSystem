package view;

import model.Change;
import model.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TillView {
    private StringBuilder output;

    public TillView() {
        output = new StringBuilder();
    }

    public void displayMessage(String message) {
        System.out.println(message);
        appendToOutput(message + "\n");
    }

    public void displayTransactionResult(int tillStart, int transactionTotal, int amountPaid, int changeTotal, Change changeGiven, int remainingAmount) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String formattedDate = formatter.format(date);

        String transactionResult = "**Receipt**\n" +
                "Date: " + formattedDate + "\n" +
                "Till Start: " + tillStart + "\n" +
                "Transaction Total: " + transactionTotal + "\n" +
                "Amount Paid: " + amountPaid + "\n" +
                "Change Total: " + changeTotal + "\n" +
                "Change Breakdown:\n" +
                formatChangeBreakdown(changeGiven.getChangeBreakdown()) + "\n" +
                "**Items Bought:**\n";

        for (Item item : changeGiven.getTransaction().getItems()) {
            transactionResult += item.getDescription() + " - " + item.getAmount() + "\n";
        }

        transactionResult += "\n**Remaining Till Amount:** " + remainingAmount + "\n" +
                "**Thank you for your purchase!**";

        System.out.println(transactionResult);
        appendToOutput(transactionResult + "\n"); // Append to optional output file (if implemented)
    }

    private String formatChangeBreakdown(Map<Integer, Integer> changeBreakdown) {
        StringBuilder breakdown = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : changeBreakdown.entrySet()) {
            breakdown.append(entry.getValue() + " x R" + entry.getKey() + "\n");
        }
        return breakdown.toString();
    }

    private void appendToOutput(String message) {
        output.append(message);
    }

    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(output.toString());
        }
    }
}
