package service.Impl;

import model.Transaction;
import repository.TransactionRepository;
import service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void recordTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(String userName) {
        return transactionRepository.findByUserName(userName);
    }
}
