package com.myTrade.unitTests.services;

import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.repositories.ConversationRepository;
import com.myTrade.services.ConversationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConversationServiceUnitTest {

    @Mock
    ConversationRepository conversationRepository;

    @InjectMocks
    ConversationService conversationService;

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
    public void whenConversationEntityIsProvided_thenShouldBeSavedIntoRepository(){
        //given
        //when
        conversationService.saveConversationEntity(conversationEntity);
        //then
        verify(conversationRepository).save(conversationEntity);
    }

    @Test
    public void whenConversationEntityIdIsProvided_thenShouldReturnConversationEntity() {
        //given
        given(conversationRepository.getById(conversationEntity.getId())).willReturn(conversationEntity);
        //when
        ConversationEntity result = conversationRepository.getById(conversationEntity.getId());
        //then
        assertThat(result).isNotNull();
        assertThat(result.getClass()).isEqualTo(conversationEntity.getClass());
    }

//    @Test TODO:[P]Problem :/
//    public void whenConversationEntityIdIsProvided_thenRetrievedNumberOfMessagesIsCorrect() {
//        //given
//        Long expectedMessageNumber = 2L;
//        given(conversationRepository.getById(conversationEntity.getId())).willReturn(conversationEntity);
//        //when
//        Long result = Long.valueOf(conversationService.findConversationMessageEntityListByConversationId(conversationEntity.getId()).size());
//        //then
//        assertThat(result).isEqualTo(expectedMessageNumber);
//    }


}