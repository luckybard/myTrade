package com.myTrade.integrationTests.service;


import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.repositories.MessageRepository;
import com.myTrade.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServiceTest {

    private MessageRepository messageRepository;
    private MessageService messageService;

    @Autowired
    public MessageServiceTest(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @Test
    public void whenMessageDtoIsProvided_shouldSetDateTimeAndBeSaved() {
        //given
        MessageDto messageDto = new MessageDto();
        messageDto.setAuthorId(1L);
        messageDto.setText("Lorem ipsum");
        //when
        messageService.saveMessage(messageDto); //TODO: [Q] Is it possible to check saved message id?
        //then
        MessageEntity actual = messageRepository.findAll().stream().filter(messageEntity -> messageEntity.getAuthorId().equals(1L)&&messageEntity.getText().equalsIgnoreCase("Lorem ipsum")).findFirst().get();
        assertThat(actual.getDateTime()).isNotNull();

    }
}
