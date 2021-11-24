package com.myTrade.utility.pojo;

import lombok.Getter;

@Getter
public enum City {
    WARSAW("WARSAW"),
    LONDON("LONDON"),
    PARIS("PARIS"),
    MOSCOW("MOSCOW"),
    PORTO("PORTO"),
    BERLIN("BERLIN"),
    EVERYWHERE("%_%");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }


}
