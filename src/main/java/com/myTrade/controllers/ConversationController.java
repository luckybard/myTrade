package com.myTrade.controllers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping(path = "/fetch/{username}")
    public ResponseEntity<Page<ConversationDto>> fetchAllConversationByUsername(@PathVariable(value = "username") String username,
                                                                                @RequestParam Optional<Integer> pageNumber, @PathVariable Optional<Integer> pageSize) {
        Page<ConversationDto> conversationDtoPage = conversationService.fetchAllConversationByUsername(username, pageNumber.orElse(0), pageSize.orElse(5));
        return ResponseEntity.ok(conversationDtoPage);
    }

    @PostMapping(path = "/save")
    public ResponseEntity saveInitialConversationWithMessageByConversationDto(@RequestBody ConversationDto conversationDto) {
        conversationService.saveInitialConversationWithMessageByConversationDto(conversationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
