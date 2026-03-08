package service;

import model.Account;
import model.TransactionResult;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountByUserNameAndPassword(Account account);

    TransactionResult deposit(Account account, double amount);

    TransactionResult withDraw(Account account, double amount);

    TransactionResult changePassword(Account account, String oldPassword,String newPassword);

    TransactionResult transferMoney(Account senderAccount,String receiverName, double amount);

}
