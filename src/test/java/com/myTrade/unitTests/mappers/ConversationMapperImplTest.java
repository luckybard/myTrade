package com.myTrade.unitTests.mappers;

import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ConversationMapperImplTest {

    ConversationMapperImpl conversationMapper = new ConversationMapperImpl();

    ConversationEntity conversationEntity = new ConversationEntity();

    @BeforeEach
    public void setUpConversationEntity() {
        conversationEntity.setId(1L);
        conversationEntity.setTitle("The LOTR book");
        conversationEntity.setRecipientId(1L);
        conversationEntity.setSenderId(2L);
        conversationEntity.setMessageList(setUpMessageEntityList());
    }

    private List<MessageEntity> setUpMessageEntityList() {
        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setId(1L);
        messageEntity1.setAuthorId(2L);
        messageEntity1.setText("Hi, I'd like to buy your book");
        messageEntity1.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 20)));

        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setId(2L);
        messageEntity2.setAuthorId(1L);
        messageEntity2.setText("Ok, nice");
        messageEntity2.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 30)));

        return List.of(messageEntity1, messageEntity2);
    }

    @Test
    public void shouldMapToDtoWithNestedObjects(){
        //given
        //when
        ConversationDto conversationDto = conversationMapper.conversationEntityToConversationDto(conversationEntity);
        //then
        assertThat(conversationDto).isNotNull();
        assertThat(conversationDto).isInstanceOf(ConversationDto.class);
        assertThat(conversationDto.getId()).isEqualTo(conversationEntity.getId());
        assertThat(conversationDto.getTitle()).isEqualTo(conversationEntity.getTitle());
        assertThat(conversationDto.getSenderId()).isEqualTo(conversationEntity.getSenderId());
        assertThat(conversationDto.getRecipientId()).isEqualTo(conversationEntity.getRecipientId());
        assertThat(conversationDto.getMessageDtoList().get(0).getClass()).isNotInstanceOf(MessageEntity.class);
    }

    @Test
    public void shouldMapToEntityWithNestedObjects(){
        //given
        ConversationDto conversationDto = conversationMapper.conversationEntityToConversationDto(conversationEntity);
        //when
        ConversationEntity conversationEntity = conversationMapper.conversationDtoToConversationEntity(conversationDto);
        //then
        assertThat(conversationEntity).isNotNull();
        assertThat(conversationEntity).isInstanceOf(ConversationEntity.class);
        assertThat(conversationEntity.getId()).isEqualTo(conversationDto.getId());
        assertThat(conversationEntity.getTitle()).isEqualTo(conversationDto.getTitle());
        assertThat(conversationEntity.getSenderId()).isEqualTo(conversationDto.getSenderId());
        assertThat(conversationEntity.getRecipientId()).isEqualTo(conversationDto.getRecipientId());
        assertThat(conversationEntity.getMessageList().get(0).getClass()).isNotInstanceOf(MessageDto.class);
    }
}
