package com.myTrade.mappersImpl;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappers.AdMapper;
import com.myTrade.utility.pojo.City;

import java.util.ArrayList;
import java.util.List;

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
        adDto.setImagePath(adEntity.getImagePath());
        adDto.setDescription(adEntity.getDescription());
        adDto.setPrice(adEntity.getPrice());
        adDto.setCity(adEntity.getCity());
        adDto.setCreatedDateTime(adEntity.getCreatedDateTime());
        adDto.setModifiedDateTime(adEntity.getModifiedDateTime());
        adDto.setIsActive(adEntity.getIsActive());
        adDto.setIsHighlighted(adEntity.getIsHighlighted());
        adDto.setCountView(adEntity.getCountView());
        adDto.setIsUserFavourite(adEntity.getIsUserFavourite());

        return adDto;
    }

    @Override
    public List<AdDto> adEntityListToAdDtoList(List<AdEntity> adEntityList) {
        if (adEntityList == null) {
            return null;
        }

        List<AdDto> list = new ArrayList<AdDto>(adEntityList.size());
        for (AdEntity adEntity : adEntityList) {
            list.add(adEntityToAdDto(adEntity));
        }

        return list;
    }

    @Override
    public AdEntity adDtoAdEntity(AdDto adDto) {
        if (adDto == null) {
            return null;
        }

        AdEntity adEntity = new AdEntity();

        adEntity.setId(adDto.getId());
        adEntity.setOwnerUsername(adDto.getOwnerUsername());
        adEntity.setAdCategory(adDto.getAdCategory());
        adEntity.setTitle(adDto.getTitle());
        adEntity.setImagePath(adDto.getImagePath());
        adEntity.setDescription(adDto.getDescription());
        adEntity.setPrice(adDto.getPrice());
        adEntity.setCity(adDto.getCity());
        adEntity.setCreatedDateTime(adDto.getCreatedDateTime());
        adEntity.setModifiedDateTime(adDto.getModifiedDateTime());
        adEntity.setIsActive(adDto.getIsActive());
        adEntity.setIsHighlighted(adDto.getIsHighlighted());
        adEntity.setCountView(adDto.getCountView());

        return adEntity;
    }

    @Override
    public List<AdEntity> adDtoListToAdEntityList(List<AdDto> adDtoList) {
        if (adDtoList == null) {
            return null;
        }

        List<AdEntity> list = new ArrayList();
        for (AdDto adDto : adDtoList) {
            list.add(adDtoAdEntity(adDto));
        }

        return list;
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
        adEditDto.setCity(City.valueOf(adEntity.getCity()));

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
        adEntity.setCity(adEditDto.getCity().getCityName());

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
        adOwnerDto.setImagePath(adEntity.getImagePath());
        adOwnerDto.setDescription(adEntity.getDescription());
        adOwnerDto.setPrice(adEntity.getPrice());
        adOwnerDto.setCity(adEntity.getCity());
        adOwnerDto.setCreatedDateTime(adEntity.getCreatedDateTime());
        adOwnerDto.setModifiedDateTime(adEntity.getModifiedDateTime());
        adOwnerDto.setIsActive(adEntity.getIsActive());
        adOwnerDto.setIsHighlighted(adEntity.getIsHighlighted());
        adOwnerDto.setCountView(adEntity.getCountView());
        adOwnerDto.setIsRefreshable(adEntity.getIsRefreshable());
        adOwnerDto.setIsUserAbleToHighlight(adEntity.getIsUserAbleToHighlight());

        return adOwnerDto;
    }
}
