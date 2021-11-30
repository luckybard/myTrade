package com.myTrade.mappersImpl;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappers.AdMapper;

public class AdMapperImpl implements AdMapper {

    @Override
    public AdDto adEntityToAdDto(AdEntity adEntity) {
        if (adEntity == null) {
            return null;
        }

        AdDto adDto = new AdDto();

        adDto.setId(adEntity.getId());
        adDto.setOwnerUsername(adEntity.getOwnerUsername());
        adDto.setAdCategory(adEntity.getAdCategory());
        adDto.setTitle(adEntity.getTitle());
        adDto.setDescription(adEntity.getDescription());
        adDto.setPrice(adEntity.getPrice());
        adDto.setCity(adEntity.getCity());
        adDto.setCreatedDate(adEntity.getCreatedDate());
        adDto.setModifiedDate(adEntity.getModifiedDate());
        adDto.setIsActive(adEntity.getIsActive());
        adDto.setIsHighlighted(adEntity.getIsHighlighted());
        adDto.setCountView(adEntity.getCountView());
        adDto.setIsUserFavourite(adEntity.getIsUserFavourite());

        return adDto;
    }

    @Override
    public AdEditDto adEntityToAdEditDto(AdEntity adEntity) {
        if (adEntity == null) {
            return null;
        }

        AdEditDto adEditDto = new AdEditDto();

        adEditDto.setId(adEntity.getId());
        adEditDto.setAdCategory(adEntity.getAdCategory());
        adEditDto.setTitle(adEntity.getTitle());
        adEditDto.setDescription(adEntity.getDescription());
        adEditDto.setPrice(adEntity.getPrice());
        adEditDto.setCity(adEntity.getCity());

        return adEditDto;
    }

    @Override
    public AdEntity adEditDtoToAdEntity(AdEditDto adEditDto) {
        if (adEditDto == null) {
            return null;
        }

        AdEntity adEntity = new AdEntity();

        adEntity.setId(adEditDto.getId());
        adEntity.setAdCategory(adEditDto.getAdCategory());
        adEntity.setTitle(adEditDto.getTitle());
        adEntity.setDescription(adEditDto.getDescription());
        adEntity.setPrice(adEditDto.getPrice());
        adEntity.setCity(adEditDto.getCity());

        return adEntity;
    }

    @Override
    public AdOwnerDto adEntityToAdOwnerDto(AdEntity adEntity) {
        if (adEntity == null) {
            return null;
        }

        AdOwnerDto adOwnerDto = new AdOwnerDto();

        adOwnerDto.setId(adEntity.getId());
        adOwnerDto.setAdCategory(adEntity.getAdCategory());
        adOwnerDto.setTitle(adEntity.getTitle());
        adOwnerDto.setDescription(adEntity.getDescription());
        adOwnerDto.setPrice(adEntity.getPrice());
        adOwnerDto.setCity(adEntity.getCity());
        adOwnerDto.setCreatedDate(adEntity.getCreatedDate());
        adOwnerDto.setModifiedDate(adEntity.getModifiedDate());
        adOwnerDto.setIsActive(adEntity.getIsActive());
        adOwnerDto.setIsHighlighted(adEntity.getIsHighlighted());
        adOwnerDto.setCountView(adEntity.getCountView());
        adOwnerDto.setIsRefreshable(adEntity.getIsRefreshable());
        adOwnerDto.setIsUserAbleToHighlight(adEntity.getIsUserAbleToHighlight());

        return adOwnerDto;
    }
}
