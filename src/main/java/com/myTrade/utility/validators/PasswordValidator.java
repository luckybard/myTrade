package com.myTrade.utility.validators;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String password) {
        //TODO: Regex to validate password
        return true;
    }
}
