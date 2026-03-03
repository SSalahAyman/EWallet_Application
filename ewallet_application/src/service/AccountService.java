package service;

import model.Account;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountByUserNameAndPassword(Account account);

    Account deposit(Account account, double amount);

    Account withDraw(Account account, double amount);

}
