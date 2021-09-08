package com.myTrade.utility.validators;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Predicate;

@Component
public class BirthDayValidator implements Predicate<LocalDate> {
    @Override
    public boolean test(LocalDate localDate) {
        if(localDate.isAfter(LocalDate.now().minusYears(18))){
            return true;
        }
        return false;
    }
}
