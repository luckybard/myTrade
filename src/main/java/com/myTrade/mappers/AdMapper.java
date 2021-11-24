package com.myTrade.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;

import java.util.List;


public interface AdMapper {
    AdDto adEntityToAdDto (AdEntity adEntity);
    List<AdDto> adEntityListToAdDtoList(List<AdEntity> adEntityList);
    AdEntity adDtoAdEntity(AdDto adDto);
    List<AdEntity> adDtoListToAdEntityList(List<AdDto> adDtoList);
    AdEditDto adEntityToAdEditDto(AdEntity adEntity);
    AdEntity adEditDtoToAdEntity(AdEditDto adEditDto);
    AdOwnerDto adEntityToAdOwnerDto(AdEntity adEntity);
}
