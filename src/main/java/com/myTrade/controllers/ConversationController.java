package com.myTrade.controllers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    private final ConversationMapperImpl conversationMapper;
    private final MessageMapperImpl messageMapper;

    @Autowired
    public ConversationController(ConversationService conversationService, ConversationRepository conversationRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
        this.conversationMapper  = new ConversationMapperImpl();
        this.messageMapper = new MessageMapperImpl();
    }

    @GetMapping(path = "/search/{id}")
    public ConversationDto findConversationDto(@PathVariable(value = "id")Long conversationId)  {
        return conversationMapper.conversationEntityToConversationDto(conversationRepository.getById(conversationId));
    }

    @GetMapping(path = "/fetch-all/{id}")
    public List<MessageDto> fetchAllMessagesDto(@PathVariable (value = "id")Long conversationId) {
        return messageMapper.messageEntityListToMessageDtoList(conversationService.findConversationMessageEntityListByConversationId(conversationId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save")
    public void saveConversation(@RequestBody ConversationDto conversationDto){
        conversationService.saveConversationEntity(conversationMapper.conversationDtoToConversationEntity(conversationDto));
    }

}
