package repository;

import model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository responsible for storing transactions in memory
 */
public class TransactionRepository {

    // List used as in-memory database
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Save transaction in repository
     */
    public void save(Transaction transaction){
        transactions.add(transaction);
    }

    /**
     * Return all transactions for specific user
     */
    public List<Transaction> findByUserName(String userName){
        return transactions.stream().filter(t -> t.getUserName().equals(userName)).collect(Collectors.toList());
    }

    /**
     * Return all transactions in system
     */
    public List<Transaction> findAll(){
        return transactions;
    }
}
