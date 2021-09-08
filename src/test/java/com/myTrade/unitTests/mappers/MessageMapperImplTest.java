package com.myTrade.unitTests.mappers;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageMapperImplTest {

    MessageMapperImpl messageMapper = new MessageMapperImpl();

    MessageEntity messageEntity = new MessageEntity();

    @BeforeEach
    public void setUpMessageEntity() {
        messageEntity.setId(1L);
        messageEntity.setAuthorId(2L);
        messageEntity.setText("Hi, I'd like to buy your book");
        messageEntity.setDateTime(LocalDateTime.of(LocalDate.of(2021, 8, 15), LocalTime.of(20, 20)));
    }

    @Test
    public void shouldMapToDtoWithNestedObjects() {
        //given
        //when
        MessageDto messageDto = messageMapper.messageEntityToMessageDto(messageEntity);
        //then
        assertThat(messageDto).isNotNull();
        assertThat(messageDto).isInstanceOf(MessageDto.class);
        assertThat(messageDto.getId()).isEqualTo(messageEntity.getId());
        assertThat(messageDto.getAuthorId()).isEqualTo(messageEntity.getAuthorId());
        assertThat(messageDto.getText()).isEqualTo(messageEntity.getText());
        assertThat(messageDto.getDateTime()).isEqualTo(messageEntity.getDateTime());
        assertThat(messageDto.getImagePath()).isEqualTo(messageEntity.getImagePath());
    }

    @Test
    public void shouldMapToEntityWithNestedObjects() {
        //given
        MessageDto messageDto = messageMapper.messageEntityToMessageDto(messageEntity);
        //when
        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        //then
        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity).isInstanceOf(MessageEntity.class);
        assertThat(messageEntity.getId()).isEqualTo(messageDto.getId());
        assertThat(messageEntity.getAuthorId()).isEqualTo(messageDto.getAuthorId());
        assertThat(messageEntity.getText()).isEqualTo(messageDto.getText());
        assertThat(messageEntity.getDateTime()).isEqualTo(messageDto.getDateTime());
        assertThat(messageEntity.getImagePath()).isEqualTo(messageDto.getImagePath());
    }




}
