package com.myTrade.utility;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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
