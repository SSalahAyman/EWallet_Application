package service.Impl;

import service.ValidationService;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateUserName(String userName) {
        if (userName.length()>3 && Character.isUpperCase(userName.charAt(0))){
            return true;
        }
        return false;
    }

    /**
     *  [a-zA-Z0-9] - This means "any letter (a-z or A-Z) or any digit (0-9)"
     *  (*) - This means "zero or more times"
     *  So [a-zA-Z0-9]* means "a string containing ONLY letters and numbers, with no other characters"
     *  The ! at the beginning means "NOT" - so we're checking if the password is NOT made up of only letters and numbers
     */
    @Override
    public boolean validatePassword(String password) {
        if (password.length() >8 && !password.matches("[a-zA-Z0-9]*")){
            return true;
        }
        return false;
    }

    @Override
    public boolean validateAge(float age) {
        if (age >=18){
            return true;
        }
        return false;
    }

    /**
     * 01 >> means All Egyptian mobile numbers start with "01"
     * [0-2,5]{1} >> means One digit that is 0,1,2,or 5 that specifies the third digit of the provider
     * [0-9]{8} >> means Exactly The remaining 8 digits can be any number 0-9
     */
    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("01[0-2,5]{1}[0-9]{8}")){
            return true;
        }
        return false;
    }


}
