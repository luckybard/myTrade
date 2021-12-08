package com.myTrade.validators.registrationRequest;

import java.util.function.Predicate;

public final class UsernameValidator implements Predicate<String> {

    @Override
    public boolean test(String username) {
        return username.length() >= 6;
    }
}
