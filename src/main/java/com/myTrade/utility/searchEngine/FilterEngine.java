package com.myTrade.utility.searchEngine;

import com.myTrade.entities.AdEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FilterEngine {

    public static List<AdEntity> filterByName(String searchName, Boolean searchInDescription, List<AdEntity> adList) {
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
}
