package com.myTrade.validators.adForm;

import com.myTrade.utility.pojo.AdCategory;

import java.util.function.Predicate;

import static com.myTrade.utility.AdUtility.AD_CATEGORIES_LIST;

public class AdCategoryValidator implements Predicate<AdCategory> {
    @Override
    public boolean test(AdCategory adCategory) {
        return AD_CATEGORIES_LIST.contains(adCategory);
    }
}
