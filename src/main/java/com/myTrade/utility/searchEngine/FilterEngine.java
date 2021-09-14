package com.myTrade.utility.searchEngine;

import com.myTrade.entities.AdEntity;
import com.myTrade.utility.AdCategory;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class FilterEngine {

    public static List<AdEntity> filterByName(String searchName, Optional<Boolean> searchInDescription, List<AdEntity> adList) {
        List<AdEntity> result = new LinkedList<>();
        if (!searchName.isEmpty()) {
            String[] providedWords = searchName.toLowerCase(Locale.ROOT).split(" ");
            for (String key : providedWords) {
                result.addAll(adList.stream().filter(ad -> ad.getTitle().toLowerCase(Locale.ROOT).contains(key) ||
                        searchInDescription.equals(true) && ad.getDescription().toLowerCase(Locale.ROOT).contains(key)).collect(Collectors.toList()));
            }
        } else result = adList;
        return result.stream().distinct().collect(Collectors.toList());
    }

    public static List<AdEntity> filterByPrice(Optional<PriceRange> priceRange, List<AdEntity> adList) {
        List<AdEntity> result = new LinkedList<>();
        if (priceRange.isPresent()) {
            result.addAll(adList.stream().filter(ad -> ad.getPrice() >= priceRange.get().getFrom() && ad.getPrice() <= priceRange.get().getTo()).collect(Collectors.toList()));
        } else result = adList;
        return result;
    }

    public static List<AdEntity> filterByCategory(Optional<AdCategory> adCategory, List<AdEntity> adList) {
        List<AdEntity> result = new LinkedList<>();
        if (adCategory.isPresent()) {
            result.addAll(adList.stream().filter(ad -> ad.getAdCategory().equals(adCategory)).collect(Collectors.toList()));
        } else result = adList;
        return result;
    }

    public static List<AdEntity> filterByCity(Optional<String> city, List<AdEntity> adList) {
        List<AdEntity> result = new LinkedList<>();
        if (city.isPresent()) {
            result.addAll(adList.stream().filter(ad -> ad.getCity().equals(city)).collect(Collectors.toList()));
        } else result = adList;
        return result;
    }


/*    public List<AdEntity> searchByDB(AdSearchRequest request, List<AdEntity> adEntityList){
        String query = "SELECT * FROM ad WHERE isActive = true";
        if(request.getAdCategory() != null) {
            query = query + " AND ad_category= " + request.getAdCategory().toString();
        }
        if(request.getPriceRange() != null) {
            query = query + " AND price BETWEEN " + request.getPriceRange().getFrom() + " AND " + request.getPriceRange().getTo();
        }
        if(request.getCity() != null) {
            query = query + " AND city= " + request.getCity();
        }
        return ;
    }*/

}
