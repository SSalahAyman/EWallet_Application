package model;

public class TransactionResult {
    private Account account;
    private String message;
    private boolean success;
    private String transactionType;   // "Deposit" , "WithDraw" , "Transfer"

    public TransactionResult() {

    }

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
