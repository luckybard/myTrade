package com.myTrade.services;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private MessageMapperImpl messageMapper = new MessageMapperImpl();

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(MessageDto messageDto) {
        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        messageDto.setDateTime(LocalDateTime.now());
        messageRepository.save(messageEntity);
    }
}
