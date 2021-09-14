package com.myTrade.utility.searchEngine;

import com.myTrade.utility.AdCategory;
import lombok.Data;

import java.util.Optional;

@Data
public class AdSearchRequest {

    private String searchByName;
    private Optional<AdCategory> adCategory;
    private Optional<PriceRange> priceRange;
    private Optional<Boolean> searchInDescription;
    private Optional<String> city;
    private SortType sortType;
    private Optional<Integer> pageNumber;
    private Optional<Integer> pageSizeNumber;
}


