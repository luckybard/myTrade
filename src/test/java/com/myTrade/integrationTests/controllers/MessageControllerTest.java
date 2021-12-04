package com.myTrade.integrationTests.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.MessageDto;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.repositories.MessageRepository;
import com.myTrade.utility.UserUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "brad@brad.brad")
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("messageDtoWithConversationId")
    public void whenProperMessageDtoAndConversationIdIsProvided_thenMessageDtoShouldBeSavedAndRetrieved201(MessageDto messageDto, Long conversationId) {
        //given
        int ONE_ADDITIONAL_MESSAGE = 1;
        int expectedMessageListSize = messageRepository.findMessageEntityListByConversationId(conversationId).size() + ONE_ADDITIONAL_MESSAGE;
        //when & then
        try {
            mockMvc.perform(post("/message/save/{id}", String.valueOf(conversationId)).contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(messageDto)))
                    .andDo(print())
                    .andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int actualMessageListSize = messageRepository.findMessageEntityListByConversationId(conversationId).size();
        assertThat(actualMessageListSize).isEqualTo(expectedMessageListSize);
    }

    public static Stream<Arguments> messageDtoWithConversationId() {
        return Stream.of(
                Arguments.of(MessageDto.builder().text("Hi mike!").build(), 1L),
                Arguments.of(MessageDto.builder().text("Nice!").build(), 1L),
                Arguments.of(MessageDto.builder().text("I'm interested").build(), 1L),
                Arguments.of(MessageDto.builder().text("Test").build(), 1L)
        );
    }

    @Test
    public void whenConversationIdIsProvided_thenMessageDtoListShouldBeFetchedByConversationIdAndRetrieved200() {
        //given
        String username = UserUtility.getUsernameFromContext();
        Long conversationId = conversationRepository
                .findConversationEntityPageByRecipientOrSenderUsername(username, Pageable.unpaged())
                .get()
                .findFirst()
                .get()
                .getId();
        //when & then
        try {
            mockMvc.perform(get("/message/fetch/list/{id}", String.valueOf(conversationId)))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
