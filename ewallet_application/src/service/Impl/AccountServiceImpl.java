package service.Impl;

import model.Account;
import model.EWalletSystem;
import model.TransactionResult;
import service.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    private EWalletSystem eWalletSystem= new EWalletSystem();
    /**
     * createAccount
     * @param account
     * @return boolean
     *         - true if account created
     *         - false if account not created (username exist)
     */
    @Override
    public Account createAccount(Account account) {
        List<Account> accounts = eWalletSystem.getListOfAccounts();
        Optional<Account> optionalAccount =accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName()) && acc.getPhoneNumber().equals(account.getPhoneNumber())).findAny();

        if (optionalAccount.isPresent()){  // means if any account in listOfAccounts is finding with same username with username && phoneNumber of Account that user created , so return false that means cannot add this account
            return null;
        }

        eWalletSystem.getListOfAccounts().add(account);  // here we access from object getListOfAccounts that return listOfAccount , and uses it to added the new created account in the signup in the list after checking on this account is present before or what in the system by checking on username
        return account;
    }

    /**
     * getAccountByUserNameAndPassword
     * @param account
     * @return boolean
     *         - true if account found with same userName & password that user used it to login
     *         - false if account not found with userName & password that user used it to login
     */
    @Override
    public Account getAccountByUserNameAndPassword(Account account) {
        List<Account> accounts = eWalletSystem.getListOfAccounts();  // Retrieve all existing account from the system
        Optional<Account> optionalAccount = accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName())).findAny(); // search for an account with matching username

        if (optionalAccount.isPresent()){
            return optionalAccount.get();
        }else {
            return null;
        }
    }

    public TransactionResult deposit(Account account, double amount){   // this function is return account in one case Or return null in two cases
        // make sure account exist in E-wallet system or not first
        // amount >=100
        List<Account> accounts = eWalletSystem.getListOfAccounts();  // Retrieve all existing account from the system
        Optional<Account> optionalAccount = accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName()) && acc.getPassword().equals(account.getPassword())).findAny(); // search for an account with matching username & password

        // Case 1: Account not found
        if(optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found!",false,"Deposit");
        }

        // Case 2: Amount less than minimum deposit (100 EGP) [After checking on account exist or not , we check on the amount]
        if (amount<100){
            return new TransactionResult(null,"❌ Deposit amount must be at least 100 EGP.",false,"Deposit");
        }

        // Success Case : if your reach for this check so that means the account is exist in system & amount is ready to deposit it because it greater than 100
        Account accountDeposit = optionalAccount.get();
        accountDeposit.setBalance(accountDeposit.getBalance() + amount);
        return new TransactionResult(accountDeposit,"✅ Deposit successful!",true,"Deposit");
    }

    @Override
    public TransactionResult withDraw(Account account, double amount) {
        List<Account> accounts = eWalletSystem.getListOfAccounts();  // Retrieve all existing account from the system
        Optional<Account> optionalAccount = accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName()) && acc.getPassword().equals(account.getPassword())).findAny(); // search for an account with matching username & password

        // Case 1: Account not found (need to check the account is exist in system or not)
        if(optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found! ",false,"withDraw");
        }
        // Case 2: Amount less than minimum withdrawal (100 EGP) [After checking on account exist or not , we check on the amount]
        if (amount<100){
            return new TransactionResult(null,"❌ Withdrawal amount must be at least 100 EGP!",false,"withDraw");
        }

        Account accountWithDraw = optionalAccount.get();
        // Case 3: Insufficient balance (checking on your balance is less than the amount that you want withDraw it)
        if (accountWithDraw.getBalance()<amount){
            return new TransactionResult(null,"❌ Insufficient balance!",false,"withDraw");
        }

        // Success Case : if your reach for this check so that means the account is exist in system & amount that user want to withDraw is ready to withDraw it because it greater than 100 & the amount that you want to withDraw it less than balance
        accountWithDraw.setBalance(accountWithDraw.getBalance() - amount);
        return new TransactionResult(accountWithDraw,"✅ withDraw successful!",true,"withDraw");
    }

    @Override
    public TransactionResult changePassword(Account account, String oldPassword, String newPassword) {
        if (newPassword.equals(account.getPassword())){
            return new TransactionResult(null,"❌ cannot change password because new pass is same old pass",false,"change password");
        } else {
            account.setPassword(newPassword);
            return new TransactionResult(account,"✅ change password successful!",true,"change password");
        }
    }


}
