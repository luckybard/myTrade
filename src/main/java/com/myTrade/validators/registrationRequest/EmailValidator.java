package com.myTrade.validators.registrationRequest;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean test(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
