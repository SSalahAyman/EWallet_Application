package service.Impl;

import model.Account;
import model.TransactionResult;
import repository.AccountRepository;
import service.AccountService;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * createAccount
     * @param account
     * @return boolean
     *         - true if account created
     *         - false if account not created (username exist)
     */
    @Override
    public Account createAccount(Account account) {
        boolean usernameExists = accountRepository
                .findByUsername(account.getUserName())
                .isPresent();

        if(usernameExists){
            return null;
        }

        boolean phoneExists = accountRepository
                .findByPhoneNumber(account.getPhoneNumber())
                .isPresent();

        if(phoneExists){
            return null;
        }

        accountRepository.save(account);  // here we access from object getListOfAccounts that return listOfAccount , and uses it to added the new created account in the signup in the list after checking on this account is present before or what in the system by checking on username
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
        return accountRepository.findByUsernameAndPassword(account.getUserName(),
                        account.getPassword())
                .orElse(null);
    }

    public TransactionResult deposit(Account account, double amount){   // this function is return account in one case Or return null in two cases
        // make sure account exist in E-wallet system or not first
        // amount >=100
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUserName(), account.getPassword()); // search for an account with matching username & password

        // Case 1: Account not found
        if(optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found!",false,"Deposit");
        }

        // Case 2: Amount less than minimum deposit (100 EGP) [After checking on account exist or not , we check on the amount]
        if (amount<100){
            return new TransactionResult(null,"❌ Deposit amount must be at least 100 EGP.",false,"Deposit");
        }

        Account accountDeposit = optionalAccount.get();
        // Case 3: Account is inactive
        if(!accountDeposit.isActive()){
            return new TransactionResult(null,"❌ Account is inactive!",false,"Deposit");
        }

        // Success Case : if your reach for this check so that means the account is exist in system & isActivated & amount is ready to deposit it because it greater than 100
        accountDeposit.setBalance(accountDeposit.getBalance() + amount);
        return new TransactionResult(accountDeposit,"✅ Deposit successful!",true,"Deposit");

    }

    @Override
    public TransactionResult withDraw(Account account, double amount) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUserName(), account.getPassword()); // search for an account with matching username & password

        // Case 1: Account not found (need to check the account is exist in system or not)
        if(optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found! ",false,"withdraw");
        }

        // Case 2: Amount less than minimum withdrawal (100 EGP) [After checking on account exist or not , we check on the amount]
        if (amount<100){
            return new TransactionResult(null,"❌ Withdrawal amount must be at least 100 EGP!",false,"withdraw");
        }

        Account accountWithDraw = optionalAccount.get();
        // Case 3: Insufficient balance (checking on your balance is less than the amount that you want withDraw it)
        if (accountWithDraw.getBalance()<amount){
            return new TransactionResult(null,"❌ Insufficient balance!",false,"withdraw");
        }

        // Case 4: Account is inactive
        if(!accountWithDraw.isActive()){
            return new TransactionResult(null,"❌ Account is inactive!",false,"withdraw");
        }

        // Success Case : if your reach for this check so that means the account is exist in system & amount that user want to withDraw is ready to withDraw it because it greater than 100 & the amount that you want to withDraw it less than balance
        accountWithDraw.setBalance(accountWithDraw.getBalance() - amount);
        return new TransactionResult(accountWithDraw,"✅ withDraw successful!",true,"withdraw");
    }

    @Override
    public TransactionResult changePassword(Account account, String oldPassword, String newPassword) {
        Optional<Account> optionalAccount =
                accountRepository.findByUsernameAndPassword(
                        account.getUserName(),
                        account.getPassword());
        if (optionalAccount.isEmpty()) {
            return new TransactionResult(null,"❌ Account not found",false,"Change Password");
        }

        Account acc = optionalAccount.get();
        if(!acc.isActive()){
            return new TransactionResult(null,"❌ Account is inactive!",false,"ChangePassword");
        }

        if (!acc.getPassword().equals(oldPassword)) {
            return new TransactionResult(null,"❌ Old password incorrect",false,"Change Password");

        } else if (newPassword.equals(oldPassword)){
            return new TransactionResult(null,"❌ cannot change password because new pass is same old pass",false,"change password");

        } else {
            acc.setPassword(newPassword);
            return new TransactionResult(acc,"✅ password changed successfully",true,"change password");
        }

    }

    @Override
    public TransactionResult transferMoney(Account senderAccount, String receiverName, double amount) {

        // case 1 : check on the sender account is exist or not in the system
        Optional<Account> optionalSender =
                accountRepository.findByUsernameAndPassword(
                        senderAccount.getUserName(),
                        senderAccount.getPassword());
        if (optionalSender.isEmpty()){
            return new TransactionResult(null,"❌ Account not found! ",false,"TransferMoney");
        }

        Account sender = optionalSender.get();

        // case 2 : Check if destination username is valid (not empty)
        if(receiverName == null || receiverName.trim().isEmpty()){
            return new TransactionResult(null,"❌ Destination username cannot be empty! ",false,"TransferMoney");
        }

        // case 3 : prevent transfer to myself
        if(sender.getUserName().equals(receiverName)){
            return new TransactionResult(null,"❌ Cannot transfer money to yourself! ",false,"TransferMoney");
        }

        // Case 4: Your Account is inactive
        if(!sender.isActive()){
            return new TransactionResult(null,"❌ Your Account is inactive!",false,"TransferMoney");
        }

        // case 5 : check if destination account is exist
        Optional<Account> optionalReceiver =
                accountRepository.findByUsername(receiverName);
        if (optionalReceiver.isEmpty()){
            return new TransactionResult(null,"❌ Receiver account not found! ",false,"TransferMoney");
        }

        Account receiver = optionalReceiver.get();

        // case 6 : receiver account is in active
        if(!receiver.isActive()){
            return new TransactionResult(null,"❌ Receiver account is inactive",false,"TransferMoney");
        }

        // case 7 : check amount > 0
        if (amount <= 0){
            return new TransactionResult(null,"❌ Transfer amount must be greater than 0 EGP! ",false,"TransferMoney");
        }

        // case 8 : Check sufficient balance
        if (amount>sender.getBalance()){
            return new TransactionResult(null , "❌ Insufficient balance in your account ",false,"TransferMoney");
        }

        // Success case : perform transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        return new TransactionResult(sender,"✅ Successfully transferred ",true,"TransferMoney");
    }

    @Override
    public TransactionResult deleteAccount(Account account) {
        Optional<Account> optionalAccount =
                accountRepository.findByUsernameAndPassword(
                        account.getUserName(),
                        account.getPassword());

        if (optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found",false,"Delete");
        }

        Account acc= optionalAccount.get();
        accountRepository.delete(acc);
        return new TransactionResult(null,"✅ Account deleted successfully",true,"Delete");
    }

    @Override
    public TransactionResult deactivateAccount(Account account) {
        Optional<Account> optionalAccount =
                accountRepository.findByUsernameAndPassword(
                        account.getUserName(),
                        account.getPassword());

        if(optionalAccount.isEmpty()){
            return new TransactionResult(null,"❌ Account not found",false,"Deactivate");
        }
        Account acc = optionalAccount.get();

        if(!acc.isActive()){
            return new TransactionResult(null,"❌ Account already inactive",false,"Deactivate");
        }

        acc.setActive(false);
        return new TransactionResult(acc,"✅ Account deactivated successfully",true,"Deactivate");

    }
}
