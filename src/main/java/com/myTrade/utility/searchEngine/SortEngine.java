package com.myTrade.utility.searchEngine;

import com.myTrade.entities.AdEntity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortEngine {

    public static List<AdEntity> sort(SortType sortType, List<AdEntity> adEntityList) {
        switch (sortType) {
            case FROM_OLDEST:
                return fromOldestSort(adEntityList);
            case FROM_NEWEST:
                return fromNewestSort(adEntityList);
            case FROM_CHEAPEST:
                return fromCheapestSort(adEntityList);
            case FROM_MOST_EXPENSIVE:
                return fromMostExpensiveSort(adEntityList);
            default:
                return normalSort(adEntityList);
        }
    }

    private static List<AdEntity> check(List<AdEntity> adEntityList) {
        List<AdEntity> adEntities = adEntityList;
        for(AdEntity adEntity: adEntities)
            if (adEntity.getExpirationHighlightTime().isBefore(LocalDateTime.now())) {
                adEntity.setIsHighlighted(true);
            }else adEntity.setIsHighlighted(false);
        return adEntities;
    }


    private static List<AdEntity> normalSort(List<AdEntity> adEntityList) {
        System.out.println(adEntityList);
        return check(adEntityList).stream().sorted(Comparator.comparing(AdEntity::getIsHighlighted).thenComparing(AdEntity::getRefreshTime)).collect(Collectors.toList());
    }

    private static List<AdEntity> fromOldestSort(List<AdEntity> adEntityList) {
        return check(adEntityList).stream().sorted(Comparator.comparing(AdEntity::getIsHighlighted).thenComparing(AdEntity::getCreatedDateTime)).collect(Collectors.toList());
    }

    private static List<AdEntity> fromNewestSort(List<AdEntity> adEntityList) {
        return check(adEntityList).stream().sorted(Comparator.comparing(AdEntity::getIsHighlighted).thenComparing(AdEntity::getCreatedDateTime).reversed()).collect(Collectors.toList());
    }

    private static List<AdEntity> fromCheapestSort(List<AdEntity> adEntityList) {
        return check(adEntityList).stream().sorted(Comparator.comparing(AdEntity::getIsHighlighted).thenComparing(AdEntity::getPrice)).collect(Collectors.toList());
    }

    private static List<AdEntity> fromMostExpensiveSort(List<AdEntity> adEntityList) {
        return check(adEntityList).stream().sorted(Comparator.comparing(AdEntity::getIsHighlighted).thenComparing(AdEntity::getPrice).reversed()).collect(Collectors.toList());
    }
}
