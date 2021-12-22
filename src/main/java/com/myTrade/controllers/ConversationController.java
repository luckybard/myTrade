package com.myTrade.controllers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping(path = "/{username}")
    @PreAuthorize("hasAuthority('conversation:read')")
    public ResponseEntity<Page<ConversationDto>> fetchAllConversationByUsername(@PathVariable(value = "username") String username,
                                                                                @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Page<ConversationDto> conversationDtoPage = conversationService.fetchAllConversationByUsername(username, pageNumber, pageSize);
        return ResponseEntity.ok(conversationDtoPage);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('message:write')")
    public ResponseEntity saveInitialConversationWithMessageByConversationDto(@RequestBody ConversationDto conversationDto) {
        conversationService.saveInitialConversationWithMessageByConversationDto(conversationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
