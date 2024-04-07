package model;

import java.util.Map;

public class Change {
    private final int totalChange;
    private final Map<Integer, Integer> changeBreakdown;
    private final Transaction transaction; // Added field

    public Change(int totalChange, Map<Integer, Integer> changeBreakdown, Transaction transaction) {
        this.totalChange = totalChange;
        this.changeBreakdown = changeBreakdown;
        this.transaction = transaction; // Assign in constructor
    }

    public int getTotalChange() {
        return totalChange;
    }

    public Map<Integer, Integer> getChangeBreakdown() {
        return changeBreakdown;
    }

    public Transaction getTransaction() { // Added getter
        return transaction;
    }
}
