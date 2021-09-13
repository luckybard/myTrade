package com.myTrade.utility.searchEngine;

import com.myTrade.entities.AdEntity;
import com.myTrade.utility.AdCategory;
import lombok.Data;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
public class SearchEngine {

    //TODO: Instead of this implementation I should create own query?

    public static List<AdEntity> search(AdSearchRequest request, List<AdEntity> adList){
        return filterByName(request.getSearchByName(), request.getSearchInDescription(),
                filterByPrice(request.getPriceRange(),
                        filterByCity(request.getCity(),
                                filterByCategory(request.getAdCategory(),
                                        adList))));
    }

    private static List<AdEntity> filterByName(String searchName, Boolean searchInDescription, List<AdEntity> adList){
        List<AdEntity> result = adList;
        if(!searchName.isEmpty()){
            result.clear();
            String [] providedWords = searchName.toLowerCase(Locale.ROOT).split(" ");
            for(String key:providedWords) {
                result.addAll(adList.stream().filter(ad -> ad.getTitle().toLowerCase(Locale.ROOT).contains(key) ||
                        searchInDescription.equals(true) && ad.getDescription().toLowerCase(Locale.ROOT).contains(key)).collect(Collectors.toList()));
            }
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    private static List<AdEntity> filterByPrice(PriceRange priceRange, List<AdEntity> adList){
        List<AdEntity> result = adList;
        if(priceRange != null){
            result.clear();
                result.addAll(adList.stream().filter(ad -> ad.getPrice() >= priceRange.getFrom() && ad.getPrice() <= priceRange.getTo()).collect(Collectors.toList()));
            }
        return adList;
    }

    private static List<AdEntity> filterByCategory(AdCategory adCategory, List<AdEntity> adList){
        List<AdEntity> result = adList;
        if(adCategory != null){
            result.clear();
            result.addAll(adList.stream().filter(ad -> ad.getAdCategory().equals(adCategory)).collect(Collectors.toList()));
        }
        return  adList;
    }

    private static List<AdEntity> filterByCity(String city, List<AdEntity> adList){
        List<AdEntity> result = adList;
        if(city.toLowerCase(Locale.ROOT) != null){
            result.clear();
            result.addAll(adList.stream().filter(ad -> ad.getCity().equals(city)).collect(Collectors.toList()));
        }
        return adList;
    }



}
