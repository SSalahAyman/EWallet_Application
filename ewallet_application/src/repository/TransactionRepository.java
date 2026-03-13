package repository;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction){
        transactions.add(transaction);
    }

    public List<Transaction> findByUserName(String userName){
        return transactions.stream().filter(t -> t.getUserName().equals(userName)).collect(Collectors.toList());
    }

    public List<Transaction> findAll(){
        return transactions;
    }
}
