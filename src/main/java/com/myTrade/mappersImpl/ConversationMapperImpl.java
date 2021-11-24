package com.myTrade.mappersImpl;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.mappers.ConversationMapper;
import org.springframework.stereotype.Component;

@Component //TODO:[Q] @Component can be autowired?
public class ConversationMapperImpl implements ConversationMapper {
    private final MessageMapperImpl messageMapper;

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
        conversationDto.setSenderUsername(conversationEntity.getSenderUsername());
        conversationDto.setRecipientUsername(conversationEntity.getRecipientUsername());
        conversationDto.setTitle(conversationEntity.getTitle());
        conversationDto.setMessageDtoList(messageMapper.messageEntityListToMessageDtoList(conversationEntity.getMessageList()));

        return conversationDto;
    }

    @Override
    public ConversationEntity conversationDtoToConversationEntity(ConversationDto conversationDto) {
        if (conversationDto == null) {
            return null;
        }

        ConversationEntity conversationEntity = new ConversationEntity();

        conversationEntity.setId(conversationDto.getId());
        conversationEntity.setSenderUsername(conversationDto.getSenderUsername());
        conversationEntity.setRecipientUsername(conversationDto.getRecipientUsername());
        conversationEntity.setTitle(conversationDto.getTitle());
        conversationEntity.setMessageList(messageMapper.messageDtoListToMessageEntityList(conversationDto.getMessageDtoList()));

        return conversationEntity;
    }
}
