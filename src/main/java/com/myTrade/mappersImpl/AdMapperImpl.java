package com.myTrade.mappersImpl;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappers.AdMapper;

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
        adDto.setExpirationHighlightTime(adEntity.getExpirationHighlightTime());
        adDto.setIsHighlighted(adEntity.getIsHighlighted());
        adDto.setRefreshTime(adEntity.getRefreshTime());
        adDto.setCountView(adEntity.getCountView());

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
        adEntity.setExpirationHighlightTime(adDto.getExpirationHighlightTime());
        adEntity.setIsHighlighted(adDto.getIsHighlighted());
        adEntity.setRefreshTime(adDto.getRefreshTime());
        adEntity.setCountView(adDto.getCountView());

        return adEntity;
    }

    @Override
    public List<AdEntity> adDtoListToAdEntityList(List<AdDto> adDtoList) {
        if (adDtoList == null) {
            return null;
        }

        List<AdEntity> list = new ArrayList<AdEntity>(adDtoList.size());
        for (AdDto adDto : adDtoList) {
            list.add(adDtoAdEntity(adDto));
        }

        return list;
    }

}
