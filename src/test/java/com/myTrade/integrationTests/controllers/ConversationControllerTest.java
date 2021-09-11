package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import com.myTrade.repositories.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationRepository conversationRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MessageMapperImpl messageMapper = new MessageMapperImpl();

    private ConversationEntity conversationEntity = new ConversationEntity();

    @BeforeEach
    private void setUpConversationEntity(){
        conversationEntity.setId(1L);
        conversationEntity.setTitle("I'd like to buy your book");
        conversationEntity.setMessageList(generateMessageEntity());
    }

    private List<MessageEntity> generateMessageEntity(){
        MessageEntity messageEntity1 = new MessageEntity(1L,2L,"desc","path", LocalDateTime.now());
        MessageEntity messageEntity2 = new MessageEntity(2L,4L,"desc","path", LocalDateTime.now());

        return List.of(messageEntity1,messageEntity2);
    }

    @Test
    @WithMockUser("user:read")
    public void whenProperConversationIdIsProvided_thenShouldReturn200(){
        //given
        Long conversationId = 1L;
        given(conversationRepository.getById(conversationEntity.getId())).willReturn(conversationEntity);
        //when & then
        try {
            mockMvc.perform(get("/conversation/search/{id}",String.valueOf(conversationId)))
                    .andExpect(status().is(200)).andExpect(content().json(objectMapper.writeValueAsString(conversationEntity)));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperConversationIdIsProvided_thenShouldReturnMessageEntityListWithStatus200() {
        //given
        Long conversationId = 1L;
        given(conversationRepository.getById(conversationEntity.getId())).willReturn(conversationEntity);
        //when & then
        try {
            mockMvc.perform(get("/conversation/fetch-all/{id}", String.valueOf(conversationId)))
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


