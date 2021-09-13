package com.myTrade.utility.searchEngine;

import com.myTrade.utility.AdCategory;
import lombok.Data;

@Data
public class AdSearchRequest {

    private String searchByName;
    private AdCategory adCategory;
    private PriceRange priceRange;
    private Boolean searchInDescription;
    private String city;
    private SortType sortType;
}


