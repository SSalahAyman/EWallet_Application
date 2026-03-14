package repository;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    public Optional<Account> findByUsernameAndPhoneNumber(String username , String phoneNumber) {
        return accounts.stream()
                .filter(acc -> acc.getUserName().equals(username) && acc.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    public Optional<Account> findByUsernameAndPassword(String username, String password) {
        return accounts.stream()
                .filter(acc ->
                        acc.getUserName().equals(username) &&
                                acc.getPassword().equals(password))
                .findFirst();
    }

    public Optional<Account> findByUsername(String username) {
        return accounts.stream()
                .filter(acc -> acc.getUserName().equals(username))
                .findFirst();
    }

    public Optional<Account> findByPhoneNumber(String phoneNumber) {
        return accounts.stream()
                .filter(acc -> acc.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    public void save(Account account) {
        accounts.add(account);
    }

    public List<Account> findAll() {
        return accounts;
    }

    public void delete(Account account){
        accounts.remove(account);
    }
}
