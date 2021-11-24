package com.myTrade.validators.registrationRequest;

import java.util.function.Predicate;

public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String password) {
        return password.length()>=6;
    }
}
