package com.myTrade.controllers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    private final ConversationMapperImpl conversationMapper = new ConversationMapperImpl();
    private final MessageMapperImpl messageMapper = new MessageMapperImpl();

    @Autowired
    public ConversationController(ConversationService conversationService, ConversationRepository conversationRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
    }

//    @GetMapping(path = "/search/{id}")
//    public ConversationDto findConversationDto(@PathVariable(value = "id")Long conversationId)  {
//        return conversationMapper.conversationEntityToConversationDto(conversationRepository.getById(conversationId));
//    }

    @GetMapping(path = "/fetchMessages/{id}")
    public List<MessageDto> fetchAllMessagesDto(@PathVariable (value = "id")Long conversationId) {//TODO:[Q] Is it better to map object in convService?
        return messageMapper.messageEntityListToMessageDtoList(conversationService.findConversationMessageEntityListByConversationId(conversationId));
    }

    @GetMapping(path = "/fetch/{username}")
    public Page<ConversationDto> fetchAllConversationByUsername(@PathVariable (value = "username")String username,
                                                                @RequestParam Optional<Integer> pageNumber, @PathVariable  Optional<Integer> pageSize) {
        return conversationService.fetchAllConversationByUsername(username,pageNumber.orElse(0),pageSize.orElse(5));
    }


    @PostMapping(path = "/save")
    public void saveInitialConversationWithMessage(@RequestBody ConversationDto conversationDto){
        conversationService.saveInitialEntityConversationWithMessageEntity(conversationMapper.conversationDtoToConversationEntity(conversationDto));
    }
}
