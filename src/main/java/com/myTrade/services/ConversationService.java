package com.myTrade.services;

import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private ConversationRepository conversationRepository;


    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void saveConversationEntity(ConversationEntity conversationEntity) {
        conversationRepository.save(conversationEntity);
    }

    public List<MessageEntity> findConversationMessageEntityListByConversationId(Long conversationId) {
        List<ConversationEntity> conversationMessageEntityListByConversationId = conversationRepository.findConversationMessageEntityListByConversationId(conversationId);
        return conversationMessageEntityListByConversationId.get(0).getMessageList();
    }

}
