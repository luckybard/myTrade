package com.myTrade.utility.pojo;

import lombok.Getter;

@Getter
public enum AdCategory {
    CLOTHES("clothes"),
    APPLIANCES("appliances"),
    BOOKS("books"),
    FURNITURE("furniture"),
    OTHER("other"),
    ALL("%_%");

    private final String category;

    AdCategory(String category) {
        this.category = category;
    }
}
