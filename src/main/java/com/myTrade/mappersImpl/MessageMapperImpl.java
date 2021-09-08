package com.myTrade.mappersImpl;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappers.MessageMapper;

import java.util.ArrayList;
import java.util.List;

public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto messageEntityToMessageDto(MessageEntity messageEntity) {
        if (messageEntity == null) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId(messageEntity.getId());
        messageDto.setAuthorId(messageEntity.getAuthorId());
        messageDto.setText(messageEntity.getText());
        messageDto.setImagePath(messageEntity.getImagePath());
        messageDto.setDateTime(messageEntity.getDateTime());

        return messageDto;
    }

    @Override
    public List<MessageDto> messageEntityListToMessageDtoList(List<MessageEntity> messageEntityList) {
        if (messageEntityList == null) {
            return null;
        }

        List<MessageDto> list = new ArrayList<MessageDto>(messageEntityList.size());
        for (MessageEntity messageEntity : messageEntityList) {
            list.add(messageEntityToMessageDto(messageEntity));
        }

        return list;
    }

    @Override
    public MessageEntity messageDtoToMessageEntity(MessageDto messageDto) {
        if (messageDto == null) {
            return null;
        }

        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setId(messageDto.getId());
        messageEntity.setAuthorId(messageDto.getAuthorId());
        messageEntity.setText(messageDto.getText());
        messageEntity.setImagePath(messageDto.getImagePath());
        messageEntity.setDateTime(messageDto.getDateTime());

        return messageEntity;
    }

    @Override
    public List<MessageEntity> messageDtoListToMessageEntityList(List<MessageDto> messageDto) {
        if (messageDto == null) {
            return null;
        }

        List<MessageEntity> list = new ArrayList<MessageEntity>(messageDto.size());
        for (MessageDto messageDto1 : messageDto) {
            list.add(messageDtoToMessageEntity(messageDto1));
        }

        return list;
    }
}
