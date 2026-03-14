package service.Impl;

import model.Transaction;
import repository.TransactionRepository;
import service.TransactionService;
import java.util.List;

/**
 * Service layer responsible for transaction operations
 */
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Record new transaction
     */
    @Override
    public void recordTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Retrieve transaction history for user
     */
    @Override
    public List<Transaction> getUserTransactions(String userName) {
        return transactionRepository.findByUserName(userName);
    }
}
