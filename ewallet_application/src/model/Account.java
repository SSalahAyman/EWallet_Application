package model;

public class Account {

    private String userName;
    private String password;
    private String phoneNumber;
    private float age;
    private double balance;

    public Account() {

    }

    // this constructor used for in the creationAccount at signup
    public Account(String userName, String password, String phoneNumber, float age) {
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

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
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

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }
}
