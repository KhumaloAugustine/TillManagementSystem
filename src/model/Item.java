package model;

public class Item {
    private String description;
    private int amount;

    public Item(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}