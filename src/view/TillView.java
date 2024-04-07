package view;

import model.Change;

import java.util.Map;

public class TillView {
    private StringBuilder output;

    public TillView() {
        output = new StringBuilder();
    }

    public void displayMessage(String message) {
        System.out.println(message);
        appendToOutput(message + "\n");
    }

    public void displayTransactionResult(int tillStart, int transactionTotal, int amountPaid, int changeTotal, Change changeGiven) {
        String transactionResult = "Till Start: " + tillStart + "\n" +
                "Transaction Total: " + transactionTotal + "\n" +
                "Amount Paid: " + amountPaid + "\n" +
                "Change Total: " + changeTotal + "\n" +
                "Change Breakdown:\n" +
                formatChangeBreakdown(changeGiven.getChangeBreakdown()) + "\n";
        System.out.println(transactionResult);
        appendToOutput(transactionResult + "\n");
    }

    private String formatChangeBreakdown(Map<Integer, Integer> changeBreakdown) {
        StringBuilder breakdown = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : changeBreakdown.entrySet()) {
            breakdown.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return breakdown.toString();
    }

    public void displayTillContents(Map<Integer, Integer> tillContents) {
        StringBuilder tillContentsStr = new StringBuilder("Till Contents:\n");
        for (Map.Entry<Integer, Integer> entry : tillContents.entrySet()) {
            tillContentsStr.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        System.out.println(tillContentsStr);
        appendToOutput(tillContentsStr + "\n");
    }

    private void appendToOutput(String message) {
        output.append(message);
    }

    public String getOutput() {
        return output.toString();
    }
}