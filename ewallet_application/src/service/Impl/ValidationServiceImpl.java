package service.Impl;

import service.ValidationService;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateUserName(String userName) {
        if (userName.length()>3){
            return true;
        }
        return false;
    }
}
