package app;
import model.Account;
import model.Transaction;
import model.TransactionResult;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.AccountService;
import service.Impl.AccountServiceImpl;
import service.Impl.TransactionServiceImpl;
import service.Impl.ValidationServiceImpl;
import service.TransactionService;
import service.ValidationService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService {
    // Scanner used to read input from user (console)
    Scanner input= new Scanner(System.in);
    // Repository layer responsible for storing accounts in memory
    private final AccountRepository accountRepository = new AccountRepository();
    // Service layer that contains business logic related to accounts
    private final AccountService accountService = new AccountServiceImpl(accountRepository);
    // Repository responsible for storing transactions
    private final TransactionRepository transactionRepository = new TransactionRepository();
    // Service responsible for transaction operations
    private final TransactionService transactionService = new TransactionServiceImpl(transactionRepository);
    // Service responsible for validating user inputs
    private final ValidationService validationService = new ValidationServiceImpl();

    /**
     * Safely read integer input from the user.
     * If user enters invalid value it will keep asking.
     */
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number, please enter a valid integer.");
            }
        }
    }

    /**
     * Safely read double input from the user.
     */
    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(input.next());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid amount, please enter a valid number.");
            }
        }
    }

    /**
     * Create a default admin account when the application starts.
     */
    private void createDefaultAdmin(){
        Account admin = new Account("IAM","IAM123","01000000000",30);   // create admin account
        admin.setAdmin(true);   // set this account as admin

        accountRepository.save(admin);   // save admin inside repository
    }

    /**
     * This method is the ENTRY POINT of the application.
     * It shows main menu (login / signup / exit)
     */
    @Override
    public void startApplication() {

        createDefaultAdmin();  // create admin when system starts

        System.out.println("E-Wallet System Started");

        boolean isExit=true;
        int counter =0;
        // Main application loop
        while (isExit){
                System.out.println("-----------------> hello sir :) <---------------------------");
                System.out.println("1.login     2.signup      3.Exit");
                System.out.println("please enter your choose...... ");
                int choose = readInt();
                switch (choose){
                    case 1 :  // Login to existing account
                        login();
                        break;
                    case 2:  // Create new account
                        signup();
                        break;
                    case 3 :  // Exit application
                        System.out.println("have a nice day :).....");
                        isExit=false;
                        break;
                    default:
                        System.out.println("Invalid choose ");
                        counter++;
                }
            // if user entered wrong option 4 times exit
                if (counter==4){
                    System.out.println("many times Invalid choose ,pls contact with admin :(.......");
                    break;
                }
            }

    }

    /**
     * Signup process
     * Responsible for creating new account
     */
    private void signup(){
        String userName;
        String password;
        int age;
        String phoneNumber;
        boolean validUserName;
        boolean validPassword;
        boolean validAge;
        boolean validPhoneNumber;

            // Validate username format
            do {
                System.out.println("please enter username : ");
                userName=input.next();
                validUserName=validationService.validateUserNameFormat(userName);
                if(!validUserName){
                    System.out.println("Invalid userName format");
                }
            }while(!validUserName);

            // Validate password format
            do {
                System.out.println("please enter password : ");
                password=input.next();
                validPassword=validationService.validatePasswordFormat(password);
                if (!validPassword){
                    System.out.println("Invalid password format");
                }
            }while(!validPassword);

            // Validate age
            do {
                System.out.println("please enter age : ");
                age = readInt();
                validAge=validationService.validateAgeFormat(age);
                if (!validAge){
                    System.out.println("Invalid age format");
                }
            }while(!validAge);

            // Validate phone number format
            do{
                System.out.println("please enter phoneNumber : ");
                phoneNumber = input.next();
                validPhoneNumber=validationService.validatePhoneNumberFormat(phoneNumber);
                if (!validPhoneNumber){
                    System.out.println("Invalid phoneNumber format");
                }
            }while(!validPhoneNumber);

            // here after validate on all input user info of the account ,create object of account and assign this info to the attributes of Account with set balance with zero
            Account account=new Account(userName,password,phoneNumber,age);

            // Call service layer to create account by storing the account with info of specific user to the wallet
            Account accountCreated=accountService.createAccount(account);

            // Check if account created successfully
            if (Objects.nonNull(accountCreated)){
                System.out.println("account created success :) ......");
                showProfile(accountCreated);   // Show account profile after signup

            } else{
                System.out.println("username or phone number already exist on the system :( .......");
            }

    }

    /**
     * Login process
     * Used to authenticate user
     */
    private void login(){
        boolean isLogin=false;
        int validAttempts=0;

        // Loop until login success or attempts finished
        while(!isLogin){
            System.out.println("please enter username : ");
            String userName=input.next();
            System.out.println("please enter password : ");
            String password=input.next();

            // create temporary account object
            Account account=new Account(userName,password);

            // check account existence
            Account accountLogin =accountService.getAccountByUserNameAndPassword(account);

            if (accountLogin != null) {
                // check if account active
                if (!accountLogin.isActive()) {
                    System.out.println("❌ Your account is inactive. Please contact support.");
                    return;   // means Exit from login service when accountLogin object not active so don't process the remain of login service
                }

                System.out.println("✅ login success :) ......");
                isLogin = true;

                // if admin open admin panel
                if (accountLogin.isAdmin()){
                    showAdminPanel(accountLogin);
                }else{
                // otherwise open user profile
                    showProfile(accountLogin);
                }

            } else {
                System.out.println("❌ Invalid userName or password :( .......");
                validAttempts++;
            }

            // if login attempts exceeded
                if(validAttempts==4){
                    System.out.println("⚠️ many times Invalid login ,pls contact with admin :(.......");
                    break;
                }

        }

    }

    /**
     * Admin panel interface
     * Allows admin to manage accounts
     */
    private void showAdminPanel(Account admin) {
        while(true){
            System.out.println("========= ADMIN PANEL =========");
            System.out.println("1. View All Accounts");
            System.out.println("2. Logout");

            int choose = readInt();

            switch(choose){
                case 1 :   // display all system accounts
                    showAllAccounts();
                    break;
                case 2 :
                    System.out.println("Admin logout...");
                    return;
                default :
                    System.out.println("invalid option");

            }
        }
    }

    /**
     * Display all accounts stored in system
     */
    private void showAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty()){
            System.out.println("No accounts found");
            return;
        }

        System.out.println("========== ALL ACCOUNTS ==========");

        // print each account details
        accounts.stream()
                .forEach(acc -> {
                    System.out.printf("""
                                    Username: %s
                                    Phone: %s
                                    Balance: %.2f
                                    Active: %b
                                    Admin: %b
                                    """,
                            acc.getUserName(),
                            acc.getPhoneNumber(),
                            acc.getBalance(),
                            acc.isActive(),
                            acc.isAdmin());
                    System.out.println("-----------------------------------");
                });
    }

    /**
     * This method shows the main profile menu for the logged-in user.
     * From here the user can perform all wallet operations.
     */
    private void showProfile(Account account){
        // Counter to track how many invalid menu choices the user entered
        int invalidCounter=0;
        // Maximum number of invalid attempts allowed
        final int maxAttempts=3;

        // Infinite loop until user logout or exit
        while(true){
            // Display all available account services
            System.out.println("1.Deposit     2.Withdraw      3.Transfer       4.Show Balance      5.Show Account Details     6.Change Password       7. Show Transaction History      8.Deactivate Account      9.Delete Account       10.Logout");
            System.out.println("please enter your choose");
            // Read user's menu choice safely
            int choose= readInt();
            // Flag to control logout
            boolean isExit= false;
            // Flag to detect invalid option
            boolean invalidChoose= false;

                switch (choose){
                    case 1:  // Deposit money
                        deposit(account);
                        break;
                    case 2:  // Withdraw money
                        withdraw(account);
                        break;
                    case 3:  // Transfer money to another account
                        transferMoney(account);
                        break;
                    case 4:  // Show current balance
                        System.out.println("Your balance is : "+ account.getBalance());
                        break;
                    case 5:  // Display full account details
                        ShowAccountDetails(account);
                        break;
                    case 6:  // Change account password
                        changePassword(account);
                        break;
                    case 7:  // Display user's transaction history
                        showTransactionHistory(account);
                        break;
                    case 8:   // Deactivate account
                        deactivateAccount(account);
                        break;
                    case 9:   // Delete account permanently
                        deleteAccount(account);
                        return;
                    case 10:   // Logout from account
                        System.out.println("logout success have a nice day .....");
                        isExit=true;
                        break;

                    default:  // Invalid option entered
                        System.out.println("Invalid choose..... ");
                        invalidCounter++;
                        invalidChoose=true;
                }

                // If user exceeded max invalid attempts
                if (invalidCounter >= maxAttempts){
                    System.out.println("many times Invalid choose ,pls contact with admin :(.......");
                    break;
                }

                // means if user enter logout don't repeat the choices again of services of account
                if (isExit){
                    break;
                }

                // means if user enter any invalid choose , skip the current statement of loop "that don't show him the part of asking Are you need to do new service" and go directly to next iteration
                if (invalidChoose){
                    continue;
                }

                // means if user finishes deposit , before repeating show the services again, ask him first if you want to do anyThing or not , so if false , break that exit from loop
                System.out.println("Are you need to do new service on your account ......:)");
                System.out.println("1.yes          2.no");
                int service=readInt();
                if(service == 2){   // If user chooses no → exit loop
                    break;
                }

        }

    }

    /**
     * Withdraw money from the user's account
     */
    private void withdraw(Account account) {
        // Print account information before withdrawal
        account.printAccountInfo(account);

        // Ask user for withdrawal amount
        System.out.println("--------------> please enter amount to withdraw.....");
        double amount = readDouble();
        // Call service layer to process withdrawal
        TransactionResult withDrawResult = accountService.withDraw(account,amount);

        // If withdrawal successful
        if(withDrawResult.isSuccess()){
            System.out.println(withDrawResult.getMessage());
            System.out.println("current & updated balance is : " + withDrawResult.getAccount().getBalance());
            // Record transaction in transaction history
            transactionService.recordTransaction(
                    new Transaction(account.getUserName(),"WITHDRAW", amount, "SUCCESS", "Withdraw successful")
            );
        } else{
            System.out.println("withDraw failed : "+withDrawResult.getMessage());
        }

        // Additional Helpful tips based on failure reason
        if(withDrawResult.getMessage().contains("not found")){
            System.out.println("💡 Tip: Please login again or contact support admin");
        } else if (withDrawResult.getMessage().contains("Insufficient")) {
            System.out.println("💡 Tip: You can deposit more money or try a smaller amount");
        } else if (withDrawResult.getMessage().contains("must be at least 100 EGP")) {
            System.out.println("💡 Tip: Minimum withdrawal amount is 100 EGP");
        }
    }

    /**
     * Deposit money into the user's account
     */
    private void deposit (Account account){
        // Show account information
        account.printAccountInfo(account);

        // Ask user for deposit amount
        System.out.println("--------------> please enter amount to deposit it.....");
        double amount = readDouble();

        // Call service layer to process deposit
        TransactionResult depositResult = accountService.deposit(account,amount);  // pass the account from signup or login and the amount that you want to deposit it to this account , and this function check on account is exist or not & amount

        // If deposit successful
        if(depositResult.isSuccess()){
            System.out.println(depositResult.getMessage());
            System.out.println("current & updated balance is : " + depositResult.getAccount().getBalance());
            // Record successful deposit transaction
            transactionService.recordTransaction(
                    new Transaction(
                            account.getUserName(),"Deposit",amount,"Success","Deposit Successful")
            );
        } else{
            System.out.println("Deposit failed : "+depositResult.getMessage());
        }

        // Additional helpful tips based on failure case
        if(depositResult.getMessage().contains("not found")){
            System.out.println("💡 Tip: Please login again or contact support admin");
        } else if (depositResult.getMessage().contains("must be at least 100 EGP")){
            System.out.println("💡 Tip: Minimum deposit amount is 100 EGP");
        }
    }

    /**
     * Change user's account password
     * Steps:
     * 1. Verify current password
     * 2. Validate new password format
     * 3. Update password
     */
    private void changePassword(Account account) {
        String inputOldPassword = "";
        String inputNewPassword ="";
        int oldPasswordAttempts=0;
        int newPasswordAttempts=0;
        final int maxAttempts=3;

        // Step 1: Verify old password (max 3 attempts)
        while (oldPasswordAttempts < maxAttempts) {
            System.out.println("--------------> please enter your current password to change it.....");
             inputOldPassword = input.next();

            boolean isPasswordMatch = validationService.validationPasswordMatch(account, inputOldPassword);
            if (isPasswordMatch) {
                System.out.println("current password verified successfully");
                break;  // exit from the loop if password matches
            } else {
                oldPasswordAttempts++;
                int remainingAttempts = maxAttempts - oldPasswordAttempts;

                if (remainingAttempts > 0){
                    System.out.println("wrong password ! you have "+remainingAttempts+" attempts remaining");
                } else{
                    System.out.println("you've exceeded the maximum number of attempts for password verification");
                    System.out.println("many times Invalid inputs ,pls contact with admin if you forget your password");
                    return; // Exit the method after reach max attempts
                }
            }

        }

        // Step 2: Enter new password (max 3 attempts)
        while (newPasswordAttempts < maxAttempts ) {
            System.out.println("--------------> please enter your new password.....");
             inputNewPassword = input.next();

            boolean isValidPasswordFormat = validationService.validatePasswordFormat(inputNewPassword);
            if (isValidPasswordFormat) {
                System.out.println("Successful password format");
                break ;  // exit from the loop if password formats success
            } else {
                newPasswordAttempts ++;
                int remainingAttempts = maxAttempts - newPasswordAttempts;

                if (remainingAttempts > 0){
                    System.out.println("wrong password ! you have "+remainingAttempts+" attempts remaining");
                } else{
                    System.out.println("you've exceeded the maximum number of attempts for entering new password");
                    return; // Exit the method after reach max attempts
                }
            }
        }

        // Step 3: Update password
        TransactionResult changePasswordResult = accountService.changePassword(account, inputOldPassword, inputNewPassword);

        if (changePasswordResult.isSuccess()) {
            System.out.println(changePasswordResult.getMessage());
            System.out.println("your new password is updated to " + changePasswordResult.getAccount().getPassword());
        } else {
            System.out.println("changePasswordResult failed : " + changePasswordResult.getMessage());
        }

    }

    private void ShowAccountDetails(Account account) {
        // Fetch the latest account data
        Account updatedAccount = accountService.getAccountByUserNameAndPassword(account);

        if (updatedAccount == null) {
            System.out.println("❌ Unable to fetch account details. Please login again.");
            return;
        }

        System.out.println("══════════════════════════════════════");
        System.out.println("         ACCOUNT DETAILS              ");
        System.out.println("══════════════════════════════════════");
        System.out.println("Username      : " + updatedAccount.getUserName());

        // Ask user if they want to see password
        System.out.println("Do you want to view your password?");
        System.out.println("1. Yes (show actual password)");
        System.out.println("2. No (show masked password)");
        System.out.print("Your choice: ");
        int choice = readInt();

        if (choice == 1) {
            System.out.println("Password      : " + updatedAccount.getPassword() + " (visible)");
        } else if (choice ==2 ) {
            String maskedPassword = updatedAccount.getPassword().replaceAll(".","*");
            System.out.println("Password      : " + maskedPassword + " (masked)");
        } else {
            System.out.println("invalid choice");
        }

        System.out.println("Phone Number  : " + updatedAccount.getPhoneNumber());
        System.out.println("Age           : " + updatedAccount.getAge());
        System.out.println("Balance       : " + updatedAccount.getBalance());
        System.out.println("══════════════════════════════════════");
    }

    /**
     * Transfer money from current account to another account
     */
    private void transferMoney(Account account) {
        System.out.println("══════════════════════════════════════");
        System.out.println("           MONEY TRANSFER              ");
        System.out.println("══════════════════════════════════════");

        // show current balance
        System.out.println("Your current balance : "+account.getBalance());

        // Get destination username
        System.out.print("Enter destination username : ");
        String destinationUserName = input.next();

        // Get amount to transfer
        System.out.print("Enter amount to transfer : ");
        double amount = readDouble();

        // Confirm transfer
        System.out.println("Transfer Summary :");
        System.out.println("From: "+account.getUserName());
        System.out.println("To: "+destinationUserName);
        System.out.println("Amount: "+amount);
        System.out.print("Confirm transfer?  (1.  Yes / 2.  No) : ");
        int confirm = readInt();

        if(confirm !=1){
            System.out.println("❌ Transfer cancelled.");
            return;
        }

        System.out.println("\n══════════════════════════════════════");
        // Perform transfer
        TransactionResult transferResult = accountService.transferMoney(account,destinationUserName,amount);
        if (transferResult.isSuccess()){
            System.out.println(transferResult.getMessage() + ": "+ amount);
            System.out.println("Your new balance : "+transferResult.getAccount().getBalance());
            transactionService.recordTransaction(
                    new Transaction(
                            transferResult.getAccount().getUserName(),
                            "TRANSFER",
                            amount,
                            "SUCCESS",
                            "Transfer to " + destinationUserName
                    )
            );

        } else {
            System.out.println("Failed Transferred : "+transferResult.getMessage());
        }
        System.out.println("══════════════════════════════════════\n");



    }

    /**
     * Display all transactions performed by the user
     */
    private void showTransactionHistory(Account account) {
        List<Transaction> transactions =
                transactionService.getUserTransactions(account.getUserName());

        if(transactions.isEmpty()){
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("========== Transaction History ==========");
        transactions.stream()
                .forEach(t -> {
                    System.out.printf("""
                                    type: %s
                                    Amount: %s
                                    Status: %s
                                    Description: %s
                                    Date: %s
                                    """,
                            t.getType(),
                            t.getAmount(),
                            t.getStatus(),
                            t.getDescription(),
                            t.getDate());
                    System.out.println("-----------------------------------");
                });
    }

    /**
     * Deactivate user's account
     * After deactivation user will not be able to login
     */
    private void deactivateAccount(Account account) {
        TransactionResult result = accountService.deactivateAccount(account);

        System.out.println(result.getMessage());

        if(result.isSuccess()){
            System.out.println("You have been logged out.");
        }

    }

    /**
     * Permanently delete account from system
     */
    private void deleteAccount(Account account) {
        System.out.println("⚠️ Are you sure you want to delete your account?");
        System.out.println("1.Yes    2.No");

        int confirm = readInt();
        if(confirm != 1){
            System.out.println("Delete cancelled.");
            return;
        }

        TransactionResult result = accountService.deleteAccount(account);
        System.out.println(result.getMessage());
        }

    }
