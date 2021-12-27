package com.myTrade.integrationTests.service;


import com.myTrade.dto.MessageDto;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.MessageService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static com.myTrade.utility.TestUtility.ONE_ADDITIONAL_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "brad@brad.brad")
public class MessageServiceTest {
    private ConversationRepository conversationRepository;
    private MessageService messageService;

    @Autowired
    public MessageServiceTest(ConversationRepository conversationRepository, MessageService messageService) {
        this.conversationRepository = conversationRepository;
        this.messageService = messageService;
    }

    @ParameterizedTest
    @MethodSource("messageDtoWithConversationId")
    public void saveMessageDtoAndAssignToConversationById_messageDtoAndConversationId_shouldSaveMessageAndAssignToConversation(MessageDto messageDto, Long conversationId) {
        //given
        int expectedConversationMessageAmount = conversationRepository.findById(conversationId).get().getMessageList().size() + ONE_ADDITIONAL_MESSAGE;
        //when
        messageService.saveMessageDtoAndAssignToConversationById(messageDto, conversationId);
        int actualConversationMessageAmount = conversationRepository.findById(conversationId).get().getMessageList().size();
        //then
        assertThat(expectedConversationMessageAmount).isEqualTo(actualConversationMessageAmount);
    }

    private static Stream<Arguments> messageDtoWithConversationId() {
        return Stream.of(
                Arguments.of(MessageDto.builder().text("Hi Adam!").build(), 1L),
                Arguments.of(MessageDto.builder().text("Nice!").build(), 1L),
                Arguments.of(MessageDto.builder().text("I'm interested").build(), 1L)
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    public void fetchMessageDtoListByConversationId_conversationId_shouldRetrieveMessageDtoList(long conversationId) {
        //given
        int FIRST_MESSAGE = 0;
        //when
        List<MessageDto> listResponseEntity = messageService.fetchMessageDtoListByConversationId(conversationId);
        //then
        assertThat(listResponseEntity).isNotNull();
        assertThat(listResponseEntity.get(FIRST_MESSAGE)).isNotNull();
        assertThat(listResponseEntity.get(FIRST_MESSAGE).getText()).isNotEmpty();
    }
}
