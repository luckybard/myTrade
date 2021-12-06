package com.myTrade.unitTests.services;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.myTrade.utility.TestUtility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConversationServiceUnitTest {

    @Mock
    ConversationRepository conversationRepository;

    @InjectMocks
    ConversationService conversationService;

    @Captor
    ArgumentCaptor<ConversationEntity> conversationEntityArgumentCaptor;

    private UserEntity user = new UserEntity();

    @BeforeEach
    private void beforeEach() {
        setUpSecurityContext(user);
        setUpDefaultUserEntity(user);
    }

    @Test
    public void whenConversationEntityIsProvided_thenShouldBeSavedIntoRepository() {
        //given
        ConversationDto conversationDto = ConversationDto.builder()
                .senderUsername(user.getUsername())
                .recipientUsername("john@john.john")
                .title("title")
                .messageDtoList(List.of(MessageDto.builder()
                        .text("hi!")
                        .authorUsername(user.getUsername())
                        .build()))
                .build();
        //when
        conversationService.saveInitialConversationWithMessageByConversationDto(conversationDto);
        //then
        verify(conversationRepository).save(conversationEntityArgumentCaptor.capture());
        assertThat(conversationEntityArgumentCaptor.getValue().getMessageList().stream().findFirst().get().getDateTime()).isNotNull();
    }

    @Test
    public void whenUsernameIsProvided_thenShouldFetchUserConversations() {
        //when
        List<ConversationEntity> conversationEntityList = getConversationEntityList();
        Page<ConversationEntity> conversationEntityPage = new PageImpl<>(conversationEntityList);
        given(conversationRepository.findConversationEntityPageByRecipientOrSenderUsername(
                user.getUsername(), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE))).willReturn(conversationEntityPage);
        //when
        conversationService.fetchAllConversationByUsername(user.getUsername(), DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        verify(conversationRepository).findConversationEntityPageByRecipientOrSenderUsername(
                user.getUsername(), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
    }
}