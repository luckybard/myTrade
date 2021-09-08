package com.myTrade.integrationTests.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.entities.MessageEntity;
import com.myTrade.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void whenProperMessageDtoIsProvided_thenRetrieved201() throws Exception {
        //given
        MessageEntity message = new MessageEntity();
        message.setAuthorId(1L);
        message.setText("Lorem ipsum");
        //when
        //then
        mockMvc.perform(post("/message/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message))).andExpect(status().is(201));
    }
}
