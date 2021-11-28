package com.myTrade.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;

public interface AdMapper {
    AdDto adEntityToAdDto (AdEntity adEntity);
    AdEditDto adEntityToAdEditDto(AdEntity adEntity);
    AdEntity adEditDtoToAdEntity(AdEditDto adEditDto);
    AdOwnerDto adEntityToAdOwnerDto(AdEntity adEntity);
}
