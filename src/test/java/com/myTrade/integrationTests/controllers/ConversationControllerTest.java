package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.utility.UserUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "brad@brad.brad", authorities = {"message:write","conversation:read"})
public class ConversationControllerTest {
    private final MockMvc mockMvc;
    private final ConversationRepository conversationRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ConversationControllerTest(MockMvc mockMvc, ConversationRepository conversationRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.conversationRepository = conversationRepository;
        this.objectMapper = objectMapper;
    }

    @Test
    public void whenUsernameIsProvided_thenShouldFetchUserConversationsAndRetrieved200() {
        //given
        String username = UserUtility.getUsernameFromContext();
        //when && then
        try {
            mockMvc.perform(get("/conversation/{username}?pageNumber=0&pageSize=2", username))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].senderUsername").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].recipientUsername").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenConversationDtoIsProvided_thenShouldSaveConversationWithInitialMessageAndRetrieved201() {
        //given
        String senderUsername = UserUtility.getUsernameFromContext();
        String recipientUsername = "john@john.john";
        String title = "Wardrobe";
        ConversationDto conversationDto = ConversationDto.builder()
                .senderUsername(senderUsername)
                .recipientUsername(recipientUsername)
                .title(title)
                .messageDtoList(List.of(MessageDto.builder()
                        .text("Hi!")
                        .authorUsername(senderUsername)
                        .build()))
                .build();
        Long ONE_ADDITIONAL_CONVERSATION = 1L;
        Long expectedConversationListSize = conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(senderUsername, Pageable.unpaged()).getTotalElements() + ONE_ADDITIONAL_CONVERSATION;
        //when & then
        try {
            mockMvc.perform(post("/conversation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(conversationDto)))
                    .andDo(print())
                    .andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
        conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(senderUsername, Pageable.unpaged());
        Long actualConversationListSize = conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(senderUsername, Pageable.unpaged()).getTotalElements();
        assertThat(actualConversationListSize).isEqualTo(expectedConversationListSize);
    }
}


