package service;

import model.Account;

public interface ValidationService {

    boolean validateUserName (String userName);

    boolean validatePassword(String password);

    boolean validateAge(float age);

    boolean validatePhoneNumber(String phoneNumber);
}
