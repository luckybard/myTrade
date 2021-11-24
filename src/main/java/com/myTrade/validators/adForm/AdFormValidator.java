package com.myTrade.validators.adForm;

import com.myTrade.dto.AdEditDto;

import java.util.function.Predicate;

public class AdFormValidator implements Predicate<AdEditDto> {
    AdCategoryValidator adCategoryValidator = new AdCategoryValidator();
    CityValidator cityValidator = new CityValidator();
    TitleValidator titleValidator = new TitleValidator();
    DescriptionValidator descriptionValidator = new DescriptionValidator();
    PriceValidator priceValidator = new PriceValidator();

    @Override
    public boolean test(AdEditDto adEditDto) {
        boolean isAdCategoryValid = adCategoryValidator.test(adEditDto.getAdCategory());
        boolean isCityValid = cityValidator.test(adEditDto.getCity());
        boolean isTitleValid = titleValidator.test(adEditDto.getTitle());
        boolean isDescriptionValid = descriptionValidator.test(adEditDto.getDescription());
        boolean isPriceValid = priceValidator.test(adEditDto.getPrice());
        return isAdCategoryValid && isCityValid && isTitleValid && isDescriptionValid && isPriceValid;
    }
}
