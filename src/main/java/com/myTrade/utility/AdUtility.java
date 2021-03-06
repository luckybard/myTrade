package com.myTrade.utility;

import com.myTrade.dto.AdEditDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;

import java.time.LocalDate;
import java.util.List;

import static com.myTrade.utility.pojo.AdCategory.*;
import static com.myTrade.utility.pojo.City.*;

public final class AdUtility {
    public static final Long INITIAL_AD_VIEW_COUNT = 0L;
    public static final int AD_VIEW_COUNT = 1;
    public static final int AD_HIGHLIGHTING_DURATION_IN_DAYS = 1;
    public static final int REFRESH_DELAY_IN_DAYS = 7;
    public static final List<City> CITIES_LIST = List.of(EVERYWHERE,WARSAW,BERLIN,PARIS,LONDON,PORTO,MOSCOW);
    public static final List<AdCategory> AD_CATEGORIES_LIST = List.of(CLOTHES,APPLIANCES,BOOKS,FURNITURE,OTHER);

    public static void checkIsUserAbleToHighlight(AdEntity adEntity, UserEntity userEntity) {
        final Integer INSUFFICIENT_NUMBER_OF_POINTS = 0;
        adEntity.setIsUserAbleToHighlight(!adEntity.getIsHighlighted() &&
                userEntity.getHighlightPoints() > INSUFFICIENT_NUMBER_OF_POINTS);
    }

    public static void setInitialValuesForAd(AdEntity adEntity, String ownerUsername) {
        Integer ONE_YEAR = 1;
        LocalDate LOCAL_DATE = LocalDate.now();
        adEntity.setCreatedDate(LOCAL_DATE);
        adEntity.setModifiedDate(LOCAL_DATE);
        adEntity.setRefreshDate(LOCAL_DATE);
        adEntity.setExpirationHighlightDate(LOCAL_DATE.minusYears(ONE_YEAR));
        adEntity.setIsActive(true);
        adEntity.setOwnerUsername(ownerUsername);
    }

    public static void setValuesFromAdEditDto(AdEntity adEntity, AdEditDto adEditDto) {
        adEntity.setTitle(adEditDto.getTitle());
        adEntity.setDescription(adEditDto.getDescription());
        adEntity.setCity(adEditDto.getCity());
        adEntity.setPrice(adEditDto.getPrice());
        adEntity.setAdCategory(adEditDto.getAdCategory());
        adEntity.setModifiedDate(LocalDate.now());
    }

    public static boolean isUserAdOwner(AdEntity adEntity, UserEntity userEntity) {
        return userEntity.getUsername().equals(adEntity.getOwnerUsername());
    }

    public static void checkIsAdRefreshable(AdEntity adEntity) {
        if(LocalDate.now().isAfter(adEntity.getRefreshDate().plusDays(REFRESH_DELAY_IN_DAYS))){
            adEntity.setIsRefreshable(true);
        }
    }
}
