package com.myTrade.mappers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;

public interface ConversationMapper {
    ConversationDto conversationEntityToConversationDto(ConversationEntity conversationEntity);

    ConversationEntity conversationDtoToConversationEntity(ConversationDto conversationDto);
}
