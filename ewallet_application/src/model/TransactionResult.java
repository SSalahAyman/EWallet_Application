package model;

/**
 * This class represents the result of any transaction operation
 * such as Deposit, Withdraw, Transfer, Change Password, etc.
 */
public class TransactionResult {
    private Account account;  // The account involved in the transaction
    private String message;   // Message explaining the result of operation
    private boolean success;  // Indicates whether operation succeeded or failed
    private String transactionType;   // Type of transaction (Deposit, Withdraw, Transfer ...)

    public TransactionResult() {

    }

    /**
     * Constructor used to create transaction result object
     */
    public TransactionResult(Account account, String message, boolean success, String transactionType){
        this.account=account;
        this.message=message;
        this.success=success;
        this.transactionType=transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
