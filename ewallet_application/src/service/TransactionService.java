package service;

import model.Transaction;

import java.util.List;

public interface TransactionService {

    void recordTransaction(Transaction transaction);

    List<Transaction> getUserTransactions (String userName);
}
