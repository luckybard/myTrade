package com.myTrade.utility.pojo;

public enum SortType {
    REFRESH("refresh_date"),
    CREATED_DATE("created_date");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
