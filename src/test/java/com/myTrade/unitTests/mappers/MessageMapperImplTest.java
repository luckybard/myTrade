package com.myTrade.unitTests.mappers;

import com.myTrade.dto.MessageDto;
import com.myTrade.entities.MessageEntity;
import com.myTrade.mappersImpl.MessageMapperImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageMapperImplTest {

    MessageMapperImpl messageMapper = new MessageMapperImpl();

    @ParameterizedTest
    @CsvFileSource(resources = "/message.csv",numLinesToSkip = 1)
    public void shouldMapMessageEntityToMessageDto(Long id,
                                                   String authorUsername,
                                                   LocalDateTime dateTime,
                                                   String text) {

        //given
        MessageEntity messageEntity = new MessageEntity(id,authorUsername,text, dateTime);
        //when
        MessageDto messageDto = messageMapper.messageEntityToMessageDto(messageEntity);
        //then
        assertThat(messageDto).isNotNull();
        assertThat(messageDto).isInstanceOf(MessageDto.class);
        assertThat(messageDto.getId()).isEqualTo(messageEntity.getId());
        assertThat(messageDto.getAuthorUsername()).isEqualTo(messageEntity.getAuthorUsername());
        assertThat(messageDto.getText()).isEqualTo(messageEntity.getText());
        assertThat(messageDto.getDateTime()).isEqualTo(messageEntity.getDateTime());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/message.csv",numLinesToSkip = 1)
    public void shouldMapToEntityWithNestedObjects(Long id,
                                                   String authorUsername,
                                                   LocalDateTime dateTime,
                                                   String text) {
        //given
        MessageDto messageDto = new MessageDto(id,authorUsername,text,dateTime);
        //when
        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDto);
        //then
        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity).isInstanceOf(MessageEntity.class);
        assertThat(messageEntity.getId()).isEqualTo(messageDto.getId());
        assertThat(messageEntity.getAuthorUsername()).isEqualTo(messageDto.getAuthorUsername());
        assertThat(messageEntity.getText()).isEqualTo(messageDto.getText());
        assertThat(messageEntity.getDateTime()).isEqualTo(messageDto.getDateTime());
    }
}
