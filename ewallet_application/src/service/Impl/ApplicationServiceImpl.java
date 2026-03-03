package service.Impl;
import model.Account;
import service.AccountService;
import service.ApplicationService;
import service.ValidationService;

import java.util.Objects;
import java.util.Scanner;

public class ApplicationServiceImpl implements ApplicationService {
    Scanner input= new Scanner(System.in);
    private AccountService accountService = new AccountServiceImpl();
    private ValidationService validationService = new ValidationServiceImpl();

    @Override
    public void startApplication() {
        boolean isExit=true;
        int counter =0;
        while (isExit){
            System.out.println("-----------------> hello sir :) <---------------------------");
            System.out.println("1.login     2.signup      3.Exit");
            System.out.println("please enter your choose...... ");
            int choose=input.nextInt();
            switch (choose){
                case 1 :
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 3 :
                    System.out.println("have a nice day :).....");
                    isExit=false;
                    break;
                default:
                    System.out.println("Invalid choose ");
                    counter++;
            }
            if (counter==4){
                System.out.println("many times Invalid choose ,pls contact with admin :(.......");
                break;
            }
        }
    }

    private void signup(){
        String userName;
        boolean validUserName;
        do {
            System.out.println("please enter username : ");
            userName=input.next();
            validUserName=validationService.validateUserName(userName);
            if(!validUserName){
                System.out.println("Invalid userName");
            }
        }while(!validUserName);

        System.out.println("please enter password : ");
        String password=input.next();
        System.out.println("please enter age : ");
        float age = input.nextFloat();
        System.out.println("please enter phoneNumber : ");
        String phoneNumber = input.next();

        Account account=new Account(userName,password,phoneNumber,age);  // here every time you signup with your information create account (object) with your Info with set yourBalance with zero

        Account accountCreated=accountService.createAccount(account);

        if (Objects.nonNull(accountCreated)){
            System.out.println("account created success :) ......");
            showProfile(accountCreated);

        } else{
            System.out.println("username already exist on the system :( .......");
        }
    }

    private void login(){
        System.out.println("please enter username : ");
        String userName=input.next();
        System.out.println("please enter password : ");
        String password=input.next();

        Account account=new Account(userName,password);

        Account accountLogin =accountService.getAccountByUserNameAndPassword(account);

        if (Objects.nonNull(accountLogin)){
            System.out.println("login success :) ......");
            showProfile(accountLogin);
        } else{
            System.out.println("Invalid userName or password :( .......");
        }
    }

    private void showProfile(Account account){
        while(true){
            System.out.println("1.Deposit     2.Withdraw      3.Transfer       4.Show Balance      5.Show Account Details     6.Change Password      7.Logout");
            System.out.println("please enter your choose");
            int choose= input.nextInt();
            int counter =0;
            boolean isExit= false;
            boolean invalidChoose= false;
            switch (choose){
                case 1:
                    deposit(account);
                    break;
                case 2:
                    withDraw(account);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:
                    System.out.println("logout success have a nice day .....");
                    isExit=true;
                    break;
                default:
                    System.out.println("Invalid choose..... ");
                    counter++;
                    invalidChoose=true;
            }

            if (counter==4){   // ❗❗ not working
                System.out.println("many times Invalid choose ,pls contact with admin :(.......");
                break;
            }

            // means if user enter logout don't repeat the choices again of services of account
            if (isExit){
                break;
            }

            // means if user enter any invalid choose , skip the current statement of loop "that don't show him the part of asking Are you need to do new service" and go directly to new iteration
            if (invalidChoose){
                continue;
            }

            // means if user finishes deposit , before repeating show the services again, ask him first if you want to do anyThing or not , so if false , break that exit from loop
            System.out.println("Are you need to do new service on your account ......:)");
            System.out.println("1.yes          2.no");
            int service=input.nextInt();
            if(service == 2){
                break;
            }
        }

    }

    private void withDraw(Account account) {
        System.out.println("--------> your details is <---------------");
        System.out.println("Username: "+account.getUserName());
        System.out.println("Password: "+account.getPassword());
        System.out.println("PhoneNumber: "+account.getPhoneNumber());
        System.out.println("Age: "+account.getAge());
        System.out.println("Balance: "+account.getBalance());

        System.out.println("--------------> please enter amount to withdraw.....");
        double amount=input.nextDouble();
        Account accountWithDrawSuccess = accountService.withDraw(account,amount);

        if(Objects.nonNull(accountWithDrawSuccess)){
            System.out.println("WithDraw Success and current balance is : " + accountWithDrawSuccess.getBalance());
        } else{
            System.out.println("withDraw failed");  // ❗❗❗❗ here I need to specific this failed to the three cases of failed withdraw with (account not exist or amount is not greater than 100 or amount greater than balance)
        }
    }

    private void deposit (Account account){
        System.out.println("--------> your details is <---------------");
        System.out.println("Username: "+account.getUserName());
        System.out.println("Password: "+account.getPassword());
        System.out.println("PhoneNumber: "+account.getPhoneNumber());
        System.out.println("Age: "+account.getAge());
        System.out.println("Balance: "+account.getBalance());

        System.out.println("--------------> please enter amount to deposit it.....");
        double amount=input.nextDouble();

        Account accountDepositSuccess = accountService.deposit(account,amount);  // pass the account from signup or login and the amount that you want to deposit it to this account , and this function check on account is exist or not & amount

        if(Objects.nonNull(accountDepositSuccess)){
            System.out.println("Deposit Success and current balance is : " + accountDepositSuccess.getBalance());
        } else{
            System.out.println("Deposit failed");  // ❗❗❗❗ here I need to specific this failed to the two cases of failed deposit with (account not exist or amount is not greater than 100)
        }

    }
}
