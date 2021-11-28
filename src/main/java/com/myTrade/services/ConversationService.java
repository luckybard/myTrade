package com.myTrade.services;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMapperImpl conversationMapper = new ConversationMapperImpl();

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void saveInitialConversationWithMessageByConversationDto(ConversationDto conversationDto) {
        ConversationEntity conversationEntity = conversationMapper.conversationDtoToConversationEntity(conversationDto);
        conversationRepository.save(conversationEntity);
    }

    public Page<ConversationDto> fetchAllConversationByUsername(String username, Integer pageNumber, Integer pageSize) {
        Page<ConversationEntity> conversationEntityPage = conversationRepository.findConversationEntityPageByRecipientUsernameOrSenderUsername(username, PageRequest.of(pageNumber, pageSize));
        Page<ConversationDto> conversationDtoPage = conversationEntityPage.map(conversationMapper::conversationEntityToConversationDto);
        return conversationDtoPage;
    }
}
