package com.myTrade.utility.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class SearchRequest {
    private String searchText;
    private AdCategory adCategory;
    private Boolean isSearchedInDescription;
    private City city;
    private Integer priceFrom;
    private Integer priceTo;
    private Integer pageNumber;
    private Integer pageSize;
}
