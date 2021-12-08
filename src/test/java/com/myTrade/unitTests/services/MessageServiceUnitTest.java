package com.myTrade.unitTests.services;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.repositories.MessageRepository;
import com.myTrade.services.MessageService;
import com.myTrade.utility.TestUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.myTrade.utility.TestUtility.setUpMessageEntityList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceUnitTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ConversationRepository conversationRepository;

    @InjectMocks
    private MessageService messageService;

    private UserEntity user = new UserEntity();

    @BeforeEach
    public void beforeEach() {
        TestUtility.setUpDefaultUserEntity(user);
        TestUtility.setUpSecurityContext(user);
    }

    @ParameterizedTest
    @MethodSource("messageDtoWithConversationId")
    public void whenProperMessageDtoAndConversationIdIsProvided_thenRetrieved201(MessageDto messageDto, Long conversationId) {
        //given
        ConversationEntity conversationEntity =
                new ConversationEntity(conversationId, user.getUsername(), "john@john.john", "title", setUpMessageEntityList());
        given(conversationRepository.getById(conversationId)).willReturn(conversationEntity);
        //when
        messageService.saveMessageDtoAndAssignToConversationById(messageDto, conversationId);
        // then
        verify(conversationRepository).getById(conversationId);
    }

    public static Stream<Arguments> messageDtoWithConversationId() {
        return Stream.of(
                Arguments.of(MessageDto.builder().text("Hi Adam!").build(), 1L),
                Arguments.of(MessageDto.builder().text("Nice!").build(), 1L),
                Arguments.of(MessageDto.builder().text("I'm interested").build(), 1L)
        );
    }

    @Test
    public void whenConversationIdIsProvided_thenMessageDtoListShouldBeFetchedByConversationId() {
        //given
        Long conversationId = 1L;
        List<MessageEntity> messageEntityList = setUpMessageEntityList();
        given(messageRepository.findMessageEntityListByConversationId(conversationId)).willReturn(messageEntityList);
        //when
        messageService.fetchMessageDtoListByConversationId(conversationId);
        // then
        verify(messageRepository).findMessageEntityListByConversationId(conversationId);
    }

}