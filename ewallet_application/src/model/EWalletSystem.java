package model;
import java.util.ArrayList;
import java.util.List;

public class EWalletSystem {

    private final String name="EraaSoft EWalletSystem";    // the name of wallet cannot override on it
    private List<Account> listOfAccounts=new ArrayList<>();  // EWallet system contain attribute "the list of created accounts for users"

    public List<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(List<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }
}
