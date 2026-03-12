package service.Impl;

import model.Account;
import service.ValidationService;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateUserNameFormat(String userName) {
        return userName !=null && userName.length() > 3 && Character.isUpperCase(userName.charAt(0));
    }

    /**
     *  [a-zA-Z0-9] - This means "any letter (a-z or A-Z) or any digit (0-9)"
     *  (*) - This means "zero or more times"
     *  So [a-zA-Z0-9]* means "a string containing ONLY letters and numbers, with no other characters"
     *  The ! at the beginning means "NOT" - so we're checking if the password is NOT made up of only letters and numbers
     */
    @Override
    public boolean validatePasswordFormat(String password) {
        return password!=null && password.length() > 8 && !password.matches("[a-zA-Z0-9]*");
    }

    @Override
    public boolean validateAgeFormat(float age) {
        return age >= 18;
    }

    /**
     * 01 >> means All Egyptian mobile numbers start with "01"
     * [0-2,5]{1} >> means One digit that is 0,1,2,or 5 that specifies the third digit of the provider
     * [0-9]{8} >> means Exactly The remaining 8 digits can be any number 0-9
     */
    @Override
    public boolean validatePhoneNumberFormat(String phoneNumber) {
        return phoneNumber!=null && phoneNumber.matches("01[0125][0-9]{8}");
    }

    @Override
    public boolean validationPasswordMatch(Account account , String inputOldPassword) {
        return inputOldPassword.equals(account.getPassword());
    }


}
