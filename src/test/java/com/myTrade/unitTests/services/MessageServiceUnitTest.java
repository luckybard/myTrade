//package com.myTrade.unitTests.services;
//
//import com.myTrade.dto.MessageDto;
//import com.myTrade.entities.MessageEntity;
//import com.myTrade.repositories.MessageRepository;
//import com.myTrade.services.MessageService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class MessageServiceUnitTest {
//
//    @Mock
//    MessageRepository messageRepository;
//
//    @InjectMocks
//    MessageService messageService;
//
//    private MessageEntity message = new MessageEntity();
//    private MessageDto messageDto = new MessageEntity();
//
//    @BeforeEach
//    public void setUpEntity(){
//        message.setId(1L);
//    }
//
//    @Test
//    void whenMessageIdIsProvided_thenReturnMessageEntity() {
//        //given
//        given(messageRepository.getById(message.getId())).willReturn((message));
//        //when
//        MessageEntity result = messageRepository.getById(message.getId());
//        //then
//        assertThat(result.getClass()).isEqualTo(MessageEntity.class);
//        assertThat(result ).isNotNull();
//    }
//
//    @Test
//    void saveMessageWhenMessageEntityIsProvided_thenMessageShouldBeSavedIntoDatabase() {
//        //given
//        //when
//        messageService.saveMessage(message);
//        //then
//        verify(messageRepository).save(message);
//    }
//}