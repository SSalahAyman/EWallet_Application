package service;

import model.Account;

public interface ValidationService {

    boolean validateUserNameFormat (String userName);

    boolean validatePasswordFormat(String password);

    boolean validateAgeFormat(float age);

    boolean validatePhoneNumberFormat(String phoneNumber);

    boolean validationPasswordMatch(Account account,String inputOldPassword);

}
