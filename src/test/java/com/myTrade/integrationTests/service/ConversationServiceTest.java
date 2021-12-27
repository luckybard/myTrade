package com.myTrade.integrationTests.service;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.myTrade.utility.TestUtility.*;
import static com.myTrade.utility.UserUtility.getUsernameFromContext;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "brad@brad.brad")
public class ConversationServiceTest {
    private ConversationService conversationService;
    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationServiceTest(ConversationService conversationService, ConversationRepository conversationRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
    }

    @Test
    public void fetchAllConversationByUsername_usernameAndPageRequest_shouldRetrieveUserConversations() {
        //given
        //when
        Page<ConversationDto> conversationDtoPage = conversationService
                .fetchAllConversationByUsername(getUsernameFromContext(), DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        assertThat(conversationDtoPage.getTotalElements()).isGreaterThan(0);
        assertThat(conversationDtoPage.getContent().stream().findFirst().get().getTitle()).isNotNull();
        assertThat(conversationDtoPage.getContent().stream().findFirst().get().getSenderUsername()).isNotNull();
        assertThat(conversationDtoPage.getContent().stream().findFirst().get().getRecipientUsername()).isNotNull();
        assertThat(conversationDtoPage.getContent().stream().findFirst().get().getMessageDtoList()).isNotEmpty();
    }

    @Test
    public void saveInitialConversationWithMessageByConversationDto_conversationDto_shouldSetInitialValueOnFirstMessage() {
        //given
        ConversationDto conversationDto = ConversationDto.builder()
                .senderUsername(getUsernameFromContext())
                .recipientUsername("john@john.john")
                .title("Wardrobe")
                .messageDtoList(List.of(MessageDto.builder()
                        .text("Hi!")
                        .authorUsername(getUsernameFromContext())
                        .build()))
                .build();
        Long expectedUserConversationListSize = conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(
                getUsernameFromContext(), Pageable.unpaged()).getTotalElements() + ONE_ADDITIONAL_CONVERSATION;
        //when
        conversationService.saveInitialConversationWithMessageByConversationDto(conversationDto);
        //then
        Long actualUserConversationListSize = conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(
                getUsernameFromContext(), Pageable.unpaged()).getTotalElements();
        assertThat(actualUserConversationListSize).isEqualTo(expectedUserConversationListSize);
    }
}
