package com.myTrade.services;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.repositories.MessageRepository;
import com.myTrade.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public final class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapperImpl messageMapper = new MessageMapperImpl();

    @Autowired
    public MessageService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public void saveMessageDtoAndAssignToConversationById(MessageDto messageDto, Long conversationId) {
        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        ConversationEntity conversationEntity = conversationRepository.getById(conversationId);
        prepareMessageToBeSavedIntoConversation(messageEntity, conversationEntity);
        conversationRepository.save(conversationEntity);
    }

    private void prepareMessageToBeSavedIntoConversation(MessageEntity messageEntity, ConversationEntity conversationEntity) {
        List<MessageEntity> messageEntityList = new ArrayList<>(conversationEntity.getMessageList());
        messageEntity.setDateTime(LocalDateTime.now());
        messageEntity.setAuthorUsername(UserUtility.getUsernameFromContext());
        messageEntityList.add(messageEntity);
        conversationEntity.setMessageList(messageEntityList);
    }

    public List<MessageDto> fetchMessageDtoListByConversationId(Long conversationId) {
        List<MessageEntity> messageEntityList = messageRepository.findMessageEntityListByConversationId(conversationId);
        return messageMapper.messageEntityListToMessageDtoList(messageEntityList);
    }
}
