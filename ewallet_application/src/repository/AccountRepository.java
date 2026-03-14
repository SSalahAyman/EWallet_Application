package repository;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    // in-memory storage for accounts
    private final List<Account> accounts = new ArrayList<>();

    /**
     * Find account by username and password (used for login)
     */
    public Optional<Account> findByUsernameAndPassword(String username, String password) {
        return accounts.stream()
                .filter(acc ->
                        acc.getUserName().equals(username) &&
                                acc.getPassword().equals(password))
                .findFirst();
    }

    /**
     * Find account by username
     */
    public Optional<Account> findByUsername(String username) {
        return accounts.stream()
                .filter(acc -> acc.getUserName().equals(username))
                .findFirst();
    }

    /**
     * Find account by phone number
     */
    public Optional<Account> findByPhoneNumber(String phoneNumber) {
        return accounts.stream()
                .filter(acc -> acc.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    /**
     * Save new account
     */
    public void save(Account account) {
        accounts.add(account);
    }

    /**
     * Return all accounts in system
     */
    public List<Account> findAll() {
        return accounts;
    }

    /**
     * Delete account from system
     */
    public void delete(Account account){
        accounts.remove(account);
    }
}
