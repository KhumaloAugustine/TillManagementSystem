package model;

import java.util.List;

public class Transaction {
    private List<Item> items;
    private int amountPaid;

    public Transaction(List<Item> items, int amountPaid) {
        this.items = items;
        this.amountPaid = amountPaid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }
}