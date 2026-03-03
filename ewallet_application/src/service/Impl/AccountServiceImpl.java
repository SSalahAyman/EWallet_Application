package service.Impl;

import model.Account;
import model.EWalletSystem;
import service.AccountService;

import java.util.List;
import java.util.Optional;

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

    public Account deposit(Account account, double amount){   // this function is return account in one case Or return null in two cases
        // make sure account exist in E-wallet system or not first
        // amount >=100
        List<Account> accounts = eWalletSystem.getListOfAccounts();  // Retrieve all existing account from the system
        Optional<Account> optionalAccount = accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName()) && acc.getPassword().equals(account.getPassword())).findAny(); // search for an account with matching username & password

        if(optionalAccount.isEmpty()){
            return null;
        }
        // if not return null from first case so , this account exist in system so we verify or check on the amount is >=100 before deposit it
        if (amount<100){
            return null;
        }
        // if your reach for this check so that means the account is exist in system 7 amount is ready to deposit it because it greater than 100
        Account accountDeposit = optionalAccount.get();
        accountDeposit.setBalance(accountDeposit.getBalance()+amount);
        return accountDeposit;
    }

    @Override
    public Account withDraw(Account account, double amount) {
        List<Account> accounts = eWalletSystem.getListOfAccounts();  // Retrieve all existing account from the system
        Optional<Account> optionalAccount = accounts.stream().filter(acc -> acc.getUserName().equals(account.getUserName()) && acc.getPassword().equals(account.getPassword())).findAny(); // search for an account with matching username & password

        if(optionalAccount.isEmpty()){
            return null;
        }
        // if not return null from first case so , this account exist in system so we verify or check on the amount that user want to withDraw must greater than 100
        if (amount<100){
            return null;
        }

        Account accountWithDraw = optionalAccount.get();
        // if not return null from second case so , this account exist in system & amount > 100 , so we need to check the amount that you want withDraw it less than your balance
        if (accountWithDraw.getBalance()<amount){
            return null;
        }

        // if your reach for this check so that means the account is exist in system & amount that user want to withDraw is ready to withDraw it because it greater than 100 & the amount that you want to withDraw it less than balance
        accountWithDraw.setBalance(accountWithDraw.getBalance() - amount);
        return accountWithDraw;
    }


}
