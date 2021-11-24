package com.myTrade.utility.pojo;

public enum SortType {
    REFRESH_TIME("refresh_time"),
    CREATED_DATE_TIME("created_date_time");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
