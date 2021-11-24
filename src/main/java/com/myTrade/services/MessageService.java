package com.myTrade.services;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapperImpl messageMapper = new MessageMapperImpl();

    @Autowired
    public MessageService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public ResponseEntity saveMessageDtoAndAssignToConversationById(MessageDto messageDto, Long conversationId) {
        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        ConversationEntity conversationEntity = conversationRepository.findById(conversationId).get();
        List<MessageEntity> messageEntityList = new ArrayList<>(conversationEntity.getMessageList());
        messageEntityList.add(messageEntity);
        conversationEntity.setMessageList(messageEntityList);
        conversationRepository.save(conversationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<MessageDto>> fetchMessageDtoListByConversationId(Long conversationId) {
        List<MessageEntity> messageEntityList = messageRepository.findMessageEntityListByConversationId(conversationId);
        List<MessageDto> messageDtoList = messageMapper.messageEntityListToMessageDtoList(messageEntityList);
        return ResponseEntity.ok(messageDtoList);
    }
}
