package com.myTrade.mappers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;

import java.util.List;



public interface ConversationMapper {
    ConversationDto conversationEntityToConversationDto(ConversationEntity conversationEntity);

    List<ConversationDto> conversationEntityListToConversationDtoList(List<ConversationEntity> conversationEntityList);

    ConversationEntity conversationDtoToConversationEntity(ConversationDto conversationDto);

    List<ConversationEntity> conversationDtoListToConversationEntityList(List<ConversationDto> conversationDtoList);
}
