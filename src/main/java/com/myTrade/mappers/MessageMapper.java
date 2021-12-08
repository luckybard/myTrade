package com.myTrade.mappers;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;

import java.util.List;

public interface MessageMapper {
    MessageDto messageEntityToMessageDto(MessageEntity messageEntity);

    List<MessageDto> messageEntityListToMessageDtoList(List<MessageEntity> messageEntityList);

    MessageEntity messageDtoToMessageEntity(MessageDto messageDto);

    List<MessageEntity> messageDtoListToMessageEntityList(List<MessageDto> messageDto);
}
