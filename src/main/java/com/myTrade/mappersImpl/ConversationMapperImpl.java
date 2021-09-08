package com.myTrade.mappersImpl;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.mappers.ConversationMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConversationMapperImpl implements ConversationMapper {

    private MessageMapperImpl messageMapper;  //initialize by constructor?

    public ConversationMapperImpl() {
        this.messageMapper = new MessageMapperImpl();
    }

    @Override
    public ConversationDto conversationEntityToConversationDto(ConversationEntity conversationEntity) {
        if (conversationEntity == null) {
            return null;
        }

        ConversationDto conversationDto = new ConversationDto();

        conversationDto.setId(conversationEntity.getId());
        conversationDto.setSenderId(conversationEntity.getSenderId());
        conversationDto.setRecipientId(conversationEntity.getRecipientId());
        conversationDto.setTitle(conversationEntity.getTitle());
        conversationDto.setMessageDtoList(messageMapper.messageEntityListToMessageDtoList(conversationEntity.getMessageList()));

        return conversationDto;
    }

    @Override
    public List<ConversationDto> conversationEntityListToConversationDtoList(List<ConversationEntity> conversationEntityList) {
        if (conversationEntityList == null) {
            return null;
        }

        List<ConversationDto> list = new ArrayList<ConversationDto>(conversationEntityList.size());
        for (ConversationEntity conversationEntity : conversationEntityList) {
            list.add(conversationEntityToConversationDto(conversationEntity));
        }

        return list;
    }

    @Override
    public ConversationEntity conversationDtoToConversationEntity(ConversationDto conversationDto) {
        if (conversationDto == null) {
            return null;
        }

        ConversationEntity conversationEntity = new ConversationEntity();

        conversationEntity.setId(conversationDto.getId());
        conversationEntity.setSenderId(conversationDto.getSenderId());
        conversationEntity.setRecipientId(conversationDto.getRecipientId());
        conversationEntity.setTitle(conversationDto.getTitle());
        conversationEntity.setMessageList(messageMapper.messageDtoListToMessageEntityList(conversationDto.getMessageDtoList()));

        return conversationEntity;
    }

    @Override
    public List<ConversationEntity> conversationDtoListToConversationEntityList(List<ConversationDto> conversationDtoList) {
        if (conversationDtoList == null) {
            return null;
        }

        List<ConversationEntity> list = new ArrayList<ConversationEntity>(conversationDtoList.size());
        for (ConversationDto conversationDto : conversationDtoList) {
            list.add(conversationDtoToConversationEntity(conversationDto));
        }

        return list;
    }
}
