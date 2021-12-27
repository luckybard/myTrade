package com.myTrade.unitTests.mappers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ConversationMapperImplTest {
    private final ConversationMapperImpl conversationMapper = new ConversationMapperImpl();

    @ParameterizedTest
    @CsvFileSource(resources = "/conversation.csv", numLinesToSkip = 1)
    public void conversationEntityToConversationDto_conversationEntity_shouldMapToDtoWithNestedObjects(Long id,
                                                                                                       String recipientUsername,
                                                                                                       String senderUsername,
                                                                                                       String title) {
        //given
        ConversationEntity conversationEntity = new ConversationEntity(id, senderUsername, recipientUsername, title,
                setUpMessageEntityList(recipientUsername, senderUsername));
        //when
        ConversationDto conversationDto = conversationMapper.conversationEntityToConversationDto(conversationEntity);
        //then
        assertThat(conversationDto).isNotNull();
        assertThat(conversationDto).isInstanceOf(ConversationDto.class);
        assertThat(conversationDto.getId()).isEqualTo(conversationEntity.getId());
        assertThat(conversationDto.getTitle()).isEqualTo(conversationEntity.getTitle());
        assertThat(conversationDto.getSenderUsername()).isEqualTo(conversationEntity.getSenderUsername());
        assertThat(conversationDto.getRecipientUsername()).isEqualTo(conversationEntity.getRecipientUsername());
        int FIRST_MESSAGE = 0;
        assertThat(conversationDto.getMessageDtoList().get(FIRST_MESSAGE).getClass()).isNotInstanceOf(MessageEntity.class);
    }

    private List<MessageEntity> setUpMessageEntityList(String recipientUsername, String senderUsername) {
        MessageEntity messageEntity0 = new MessageEntity();
        messageEntity0.setId(1L);
        messageEntity0.setAuthorUsername(recipientUsername);
        messageEntity0.setText("Hi, I'd like to buy your book");
        messageEntity0.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 20)));

        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setId(2L);
        messageEntity1.setAuthorUsername(senderUsername);
        messageEntity1.setText("Ok, nice");
        messageEntity1.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 30)));

        return List.of(messageEntity0, messageEntity1);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/conversation.csv", numLinesToSkip = 1)
    public void conversationDtoToConversationEntity_conversationDto_shouldMapToEntityWithNestedObjects(Long id,
                                                                                                       String recipientUsername,
                                                                                                       String senderUsername,
                                                                                                       String title) {
        //given
        ConversationDto conversationDto = new ConversationDto(id, senderUsername, recipientUsername, title,
                setUpMessageDtoList(recipientUsername, senderUsername));
        //when
        ConversationEntity conversationEntity = conversationMapper.conversationDtoToConversationEntity(conversationDto);
        //then
        assertThat(conversationEntity).isNotNull();
        assertThat(conversationEntity).isInstanceOf(ConversationEntity.class);
        assertThat(conversationEntity.getId()).isEqualTo(conversationDto.getId());
        assertThat(conversationEntity.getTitle()).isEqualTo(conversationDto.getTitle());
        assertThat(conversationEntity.getSenderUsername()).isEqualTo(conversationDto.getSenderUsername());
        assertThat(conversationEntity.getRecipientUsername()).isEqualTo(conversationDto.getRecipientUsername());
        assertThat(conversationEntity.getMessageList().get(0).getClass()).isNotInstanceOf(MessageDto.class);
    }

    private List<MessageDto> setUpMessageDtoList(String recipientUsername, String senderUsername) {
        MessageDto messageDto0 = new MessageDto();
        messageDto0.setId(1L);
        messageDto0.setAuthorUsername(recipientUsername);
        messageDto0.setText("Hi, I'd like to buy your book");
        messageDto0.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 20)));

        MessageDto messageDto1 = new MessageDto();
        messageDto1.setId(2L);
        messageDto1.setAuthorUsername(senderUsername);
        messageDto1.setText("Ok, nice");
        messageDto1.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 30)));

        return List.of(messageDto0, messageDto1);
    }
}
