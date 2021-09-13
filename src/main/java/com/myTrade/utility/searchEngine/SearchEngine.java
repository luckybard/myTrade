package com.myTrade.utility.searchEngine;

import com.myTrade.entities.AdEntity;
import lombok.Data;

import java.util.List;

import static com.myTrade.utility.searchEngine.FilterEngine.*;

@Data
public class SearchEngine {
    public static List<AdEntity> search(AdSearchRequest request, List<AdEntity> adList) {
        return filterByName(request.getSearchByName(), request.getSearchInDescription(),
                filterByPrice(request.getPriceRange(),
                        filterByCity(request.getCity(),
                                filterByCategory(request.getAdCategory(),
                                        adList))));
    }


}
