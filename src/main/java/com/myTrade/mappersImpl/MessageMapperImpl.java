package com.myTrade.mappersImpl;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappers.MessageMapper;

public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto messageEntityToMessageDto(MessageEntity messageEntity) {
        if (messageEntity == null) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId(messageEntity.getId());
        messageDto.setAuthorUsername(messageEntity.getAuthorUsername());
        messageDto.setText(messageEntity.getText());
        messageDto.setDateTime(messageEntity.getDateTime());

        return messageDto;
    }

    @Override
    public MessageEntity messageDtoToMessageEntity(MessageDto messageDto) {
        if (messageDto == null) {
            return null;
        }

        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setId(messageDto.getId());
        messageEntity.setAuthorUsername(messageDto.getAuthorUsername());
        messageEntity.setText(messageDto.getText());

        return messageEntity;
    }
}
