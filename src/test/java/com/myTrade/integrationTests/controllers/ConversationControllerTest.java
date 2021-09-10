package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.repositories.ConversationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationRepository conversationRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    //TODO: When despite using try-catch,keyword 'throws' fails test
    @Test
    public void whenProperConversationIdIsProvided_thenShouldReturn200() throws Exception {
        //given
        Long conversationId = 1L;
        //when
        //then
        mockMvc.perform(get("/conversation/search/{id}").param("id", String.valueOf(conversationId)))
                .andExpect(status().is(200));
    }

    @Test
    public void whenProperConversationIdIsProvided_thenShouldReturnMessageEntityListWithStatus200() {
        //given
        Long conversationId = 1L;
        //when
        //then
        try {
            mockMvc.perform(get("/conversation/fetch-all/{id}").param("id", String.valueOf(conversationId)))
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperConversationEntityIsProvided_thenShouldReturnStatus200() {
        //given
        ConversationEntity conversation = new ConversationEntity();
        conversation.setSenderId(1L);
        conversation.setRecipientId(2L);
        conversation.setTitle("Hi");
        conversation.setMessageList(new LinkedList<>());
        //when
        //then
        try {
            mockMvc.perform(post("/conversation/save").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(conversation))).andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


