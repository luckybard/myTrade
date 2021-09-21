package com.myTrade.utility;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class AdSearchRequest {
    private String text;
    private Boolean searchInDescription;
    private AdCategory category;
    private PriceRange priceRange;
    private City city;
    private PageRequest pageRequest;
    private SortType sortType;
}


