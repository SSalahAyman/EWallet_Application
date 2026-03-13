package model;

public class Account {

    private String userName;
    private String password;
    private String phoneNumber;
    private int age;
    private double balance;
    private boolean active = true;

    public Account() {

    }

    // this constructor used for in the creationAccount at signup
    public Account(String userName, String password, String phoneNumber, int age) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.balance=0;
    }

    // this constructor used for in the getAccount at login
    public Account(String userName,String password){
        this.userName=userName;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }

    public void printAccountInfo(Account account){
        System.out.printf("""
            Username: %s
            Password: %s
            Phone: %s
            Age: %d
            Balance: %.2f
            """,
                account.getUserName(),
                account.getPassword(),
                account.getPhoneNumber(),
                account.getAge(),
                account.getBalance());
    }
}
