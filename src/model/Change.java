package model;

import java.util.Map;

public class Change {
    private int totalChange;
    private Map<Integer, Integer> changeBreakdown;

    public Change(int totalChange, Map<Integer, Integer> changeBreakdown) {
        this.totalChange = totalChange;
        this.changeBreakdown = changeBreakdown;
    }

    public int getTotalChange() {
        return totalChange;
    }

    public Map<Integer, Integer> getChangeBreakdown() {
        return changeBreakdown;
    }
}