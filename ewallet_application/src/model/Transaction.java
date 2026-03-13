package model;

import java.time.LocalDateTime;

public class Transaction {

    private String userName;
    private String type;
    private double amount;
    private String status;
    private String description;
    private LocalDateTime date;


    public Transaction(String userName, String type, double amount, String status, String description) {
        this.userName = userName;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
