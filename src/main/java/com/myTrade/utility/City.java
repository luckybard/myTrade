package com.myTrade.utility;

import lombok.Getter;

@Getter
public enum City {
    WARSAW("Warsaw"),
    LONDON("London"),
    PARIS("Paris"),
    MOSCOW("Moscow"),
    PORTO("Porto"),
    BERLIN("Berlin"),
    EVERYWHERE("%_%");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }


}
