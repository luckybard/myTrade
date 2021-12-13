package com.myTrade.controllers;

import com.myTrade.dto.MessageDto;
import com.myTrade.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/message")
public final class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{id}")
    public ResponseEntity saveMessage(@RequestBody MessageDto messageDto, @PathVariable(value = "id") Long conversationId) {
        messageService.saveMessageDtoAndAssignToConversationById(messageDto, conversationId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<MessageDto>> fetchMessageDtoListByConversationId(@PathVariable(value = "id") Long conversationId) {
        List<MessageDto> messageDtoList = messageService.fetchMessageDtoListByConversationId(conversationId);
        return ResponseEntity.ok(messageDtoList);
    }
}
