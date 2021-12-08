package com.myTrade.validators.registrationRequest;

import java.util.function.Predicate;

public final class PasswordValidator implements Predicate<String> {

    @Override
    public boolean test(String password) {
        return password.length() >= 6;
    }
}
