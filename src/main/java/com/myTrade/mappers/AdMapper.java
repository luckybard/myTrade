package com.myTrade.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;

import java.util.List;
import java.util.Queue;


public interface AdMapper {

    AdDto adEntityToAdDto (AdEntity adEntity);

    List<AdDto> adEntityListToAdDtoList(List<AdEntity> adEntityList);

    AdEntity adDtoAdEntity(AdDto adDto);

    List<AdEntity> adDtoListToAdEntityList(List<AdDto> adDtoList);

    Queue<AdDto> adEntityQueueToAdDtoQueue(Queue<AdEntity> adEntityQueue);

    Queue<AdEntity> adDtoQueueToAdEntityQueue(Queue<AdDto> adDtoQueue);






}
