package com.myTrade.validators.adForm;

import com.myTrade.dto.AdEditDto;

import java.util.function.Predicate;

public final class AdFormValidator implements Predicate<AdEditDto> {
    private final AdCategoryValidator adCategoryValidator = new AdCategoryValidator();
    private final CityValidator cityValidator = new CityValidator();
    private final TitleValidator titleValidator = new TitleValidator();
    private final DescriptionValidator descriptionValidator = new DescriptionValidator();
    private final PriceValidator priceValidator = new PriceValidator();

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
