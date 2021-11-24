package com.myTrade.validators.adForm;

import java.util.function.Predicate;

public class PriceValidator implements Predicate<Double> {
   @Override
    public boolean test(Double price) {
        return price > 0;
    }
}
