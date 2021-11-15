package com.myTrade.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;

import java.util.List;


public interface AdMapper {
    AdDto adEntityToAdDto (AdEntity adEntity);

    List<AdDto> adEntityListToAdDtoList(List<AdEntity> adEntityList);

    AdEntity adDtoAdEntity(AdDto adDto);

    List<AdEntity> adDtoListToAdEntityList(List<AdDto> adDtoList);






}
