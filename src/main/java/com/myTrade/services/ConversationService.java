package com.myTrade.services;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {

    private ConversationRepository conversationRepository;
    private ConversationMapperImpl conversationMapper = new ConversationMapperImpl();


    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void saveInitialEntityConversationWithMessageEntity(ConversationEntity conversationEntity) {
        conversationEntity.getMessageList().get(0).setDateTime(LocalDateTime.now());
        conversationRepository.save(conversationEntity);
    }

    public List<MessageEntity> findConversationMessageEntityListByConversationId(Long conversationId) {
        return conversationRepository.findConversationMessageEntityListByConversationId(conversationId).get(0).getMessageList();
    }

    public Page<ConversationDto> fetchAllConversationByUsername(String username, Integer pageNumber, Integer pageSize) {
        return conversationRepository.findByRecipientUsernameOrSenderUsername(username, PageRequest.of(pageNumber, pageSize)).map(conversationMapper::conversationEntityToConversationDto);
    }


}
