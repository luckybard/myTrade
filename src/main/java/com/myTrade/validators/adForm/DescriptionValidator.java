package com.myTrade.validators.adForm;

import java.util.function.Predicate;

public class DescriptionValidator implements Predicate<String> {
    @Override
    public boolean test(String description) {
        return description.length()>=20;
    }
}
