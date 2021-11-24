package com.myTrade.controllers;

import com.myTrade.dto.MessageDto;
import com.myTrade.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/save/{id}")
    public ResponseEntity saveMessage(@RequestBody MessageDto messageDto, @PathVariable(value = "id") Long conversationId){
        return messageService.saveMessageDtoAndAssignToConversationById(messageDto,conversationId);
    }

    @GetMapping("/fetch/list/{id}")
    public ResponseEntity<List<MessageDto>> fetchMessageDtoListByConversationId(@PathVariable(value = "id") Long conversationId) {
        return messageService.fetchMessageDtoListByConversationId(conversationId);
    }
}
