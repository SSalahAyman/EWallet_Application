package model;

import java.time.LocalDateTime;

public class Transaction {

    private String userName;  // username who performed the transaction
    private String type;     // type of transaction (Deposit / Withdraw / Transfer)
    private double amount;  // amount involved in transaction
    private String status;   // transaction status (SUCCESS / FAILED)
    private String description;  // additional description
    private LocalDateTime date;  // date and time when transaction occurred

    /**
     * Constructor to create transaction record
     */
    public Transaction(String userName, String type, double amount, String status, String description) {
        this.userName = userName;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.date = LocalDateTime.now();   // automatically record transaction time
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
