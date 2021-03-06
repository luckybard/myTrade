package com.myTrade.services;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public final class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMapperImpl conversationMapper = new ConversationMapperImpl();

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void saveInitialConversationWithMessageByConversationDto(ConversationDto conversationDto) {
        ConversationEntity conversationEntity = conversationMapper.conversationDtoToConversationEntity(conversationDto);
        final int FIRST_MESSAGE = 0;
        conversationEntity.getMessageList().get(FIRST_MESSAGE).setDateTime(LocalDateTime.now());
        conversationRepository.save(conversationEntity);
    }

    public Page<ConversationDto> fetchAllConversationByUsername(String username, Integer pageNumber, Integer pageSize) {
        Page<ConversationEntity> conversationEntityPage = conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(username, PageRequest.of(pageNumber, pageSize));
        return conversationEntityPage.map(conversationMapper::conversationEntityToConversationDto);
    }
}
