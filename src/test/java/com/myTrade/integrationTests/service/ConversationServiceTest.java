package com.myTrade.integrationTests.service;

import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConversationServiceTest {

    private ConversationService conversationService;
    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationServiceTest(ConversationService conversationService, ConversationRepository conversationRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
    }

    @Test
    void whenConversationEntityIsProvided_thenShouldBeSaved() {
        //given
        Long expectedConversationListSize = conversationRepository.count() + 1;
        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setSenderId(1L);
        conversationEntity.setRecipientId(2L);
        conversationEntity.setTitle("Hi");
        conversationEntity.setMessageList(new LinkedList<>());
        //when
        conversationService.saveConversationEntity(conversationEntity);
        Long actualConversationListSize = conversationRepository.count();
        //then
        assertThat(expectedConversationListSize).isEqualTo(actualConversationListSize);
    }

    @Test
    @Transactional
    void findConversationMessageEntityListByConversationId() {
        //given
        long conversationId = 1L;
        //when
        List<MessageEntity> conversationEntityList = conversationService.findConversationMessageEntityListByConversationId(conversationId);
        //then
        assertThat(conversationEntityList).isNotEmpty();

    }
}
