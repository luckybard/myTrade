package com.myTrade.services;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private MessageMapperImpl messageMapper = new MessageMapperImpl();
    private ConversationService conversationService;
    private ConversationRepository conversationRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationService conversationService, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
    }
    //TODO:[Q] A lot of stuff is going on here. Is it better to save everything together (don't split saving messageEntity with saving conversationEntity)
    public void saveMessage(MessageDto messageDto, Long conversationId) {

        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        messageEntity.setDateTime(LocalDateTime.now());
        messageRepository.save(messageEntity);
        ConversationEntity conversationEntity = conversationRepository.findById(conversationId).get();
        List<MessageEntity> messageEntityList = conversationEntity.getMessageList();
        messageEntityList.add(messageEntity);
        conversationEntity.setMessageList(messageEntityList);
        conversationRepository.save(conversationEntity);
    }
}
