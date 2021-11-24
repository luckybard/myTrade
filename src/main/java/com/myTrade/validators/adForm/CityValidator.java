package com.myTrade.validators.adForm;

import com.myTrade.utility.pojo.City;

import java.util.function.Predicate;

import static com.myTrade.utility.AdUtility.CITIES_LIST;


public class CityValidator implements Predicate<City> {
    @Override
    public boolean test(City city) {
        return CITIES_LIST.contains(city);
    }
}
