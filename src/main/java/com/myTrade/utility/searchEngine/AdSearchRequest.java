package com.myTrade.utility.searchEngine;

import com.myTrade.utility.AdCategory;
import com.myTrade.utility.City;
import lombok.Data;

@Data
public class AdSearchRequest {

    private String searchByName;
    private AdCategory adCategory;
    private PriceRange priceRange;
    private Boolean searchInDescription;
    private City city;
    private SortType sortType;
}


