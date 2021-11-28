package com.myTrade.mappers;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;

public interface MessageMapper {
    MessageDto messageEntityToMessageDto(MessageEntity messageEntity);
    MessageEntity messageDtoToMessageEntity(MessageDto messageDto);
}
