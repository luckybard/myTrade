package com.myTrade.controllers;

import com.myTrade.dto.MessageDto;
import com.myTrade.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save/{id}")
    public void saveMessage(@RequestBody MessageDto messageDto, @PathVariable(value = "id") Long conversationId){
        messageService.saveMessage(messageDto,conversationId);
    }
}
