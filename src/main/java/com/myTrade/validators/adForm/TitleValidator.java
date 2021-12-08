package com.myTrade.validators.adForm;

import java.util.function.Predicate;

public final class TitleValidator implements Predicate<String> {

    @Override
    public boolean test(String title) {
        return title.length() >= 6;
    }
}
