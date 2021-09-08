package com.myTrade.utility.validators;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;


@Component
public class UserNameValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return true;
    }
}
