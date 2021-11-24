package com.myTrade.services;

import com.myTrade.dto.ConversationDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMapperImpl conversationMapper = new ConversationMapperImpl();

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ResponseEntity saveInitialConversationWithMessageByConversationDto(ConversationDto conversationDto) {
        ConversationEntity conversationEntity = conversationMapper.conversationDtoToConversationEntity(conversationDto);
        conversationRepository.save(conversationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Page<ConversationDto>> fetchAllConversationByUsername(String username, Integer pageNumber, Integer pageSize) {
        Page<ConversationEntity> conversationEntityPage = conversationRepository.findConversationEntityPageByRecipientUsernameOrSenderUsername(username, PageRequest.of(pageNumber, pageSize));
        Page<ConversationDto> conversationDtoPage = conversationEntityPage.map(conversationMapper::conversationEntityToConversationDto);
        return ResponseEntity.ok(conversationDtoPage);
    }
}
