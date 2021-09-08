package com.myTrade.unitTests.mappers;


import com.myTrade.dto.AdDto;
import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.MessageDto;
import com.myTrade.dto.UserDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//is it Unit test?
class UserMapperImplTest {

    UserMapperImpl userMapper = new UserMapperImpl();

    UserEntity user = new UserEntity();

    @BeforeEach
    private void setUpUserEntity() {
        user.setUserName("bart");
        user.setPassword("uniquePassword");
        user.setEmail("bart@bart.com");
        user.setBirthDate(LocalDate.of(1990,8,10));
        user.setAdEntityList(setUpAdEntityList());
        user.setConversationEntityList(setUpConversationEntityList());
    }

    private List<AdEntity> setUpAdEntityList() {
        AdEntity adEntity1 = new AdEntity();
        adEntity1.setId(1L);
        adEntity1.setOwnerId(1L);
        adEntity1.setAdCategory(AdCategory.BOOKS);
        adEntity1.setTitle("The Lord of the rings");
        adEntity1.setImagePath("image/path");
        adEntity1.setDescription("The best book");
        adEntity1.setPrice(40.00);
        adEntity1.setCity("Warsaw");
        adEntity1.setCreatedDateTime(LocalDateTime.of(LocalDate.of(2020, 8, 21), LocalTime.of(20, 18)));
        adEntity1.setModifiedDateTime(LocalDateTime.now());

        AdEntity adEntity2 = new AdEntity();
        adEntity2.setId(2L);
        adEntity2.setOwnerId(1L);
        adEntity2.setAdCategory(AdCategory.FURNITURE);
        adEntity2.setTitle("Antique chair");
        adEntity2.setImagePath("image/path");
        adEntity2.setDescription("Comfortable chair");
        adEntity2.setPrice(60.00);
        adEntity2.setCity("Warsaw");
        adEntity2.setCreatedDateTime(LocalDateTime.of(LocalDate.of(2020, 8, 21), LocalTime.of(20, 18)));
        adEntity2.setModifiedDateTime(LocalDateTime.now());

        AdEntity adEntity3 = new AdEntity();
        adEntity3.setId(3L);
        adEntity3.setOwnerId(1L);
        adEntity3.setAdCategory(AdCategory.CLOTHES);
        adEntity3.setTitle("AC/DC T-shirt");
        adEntity3.setImagePath("image/path");
        adEntity3.setDescription("XL Size");
        adEntity3.setPrice(80.25);
        adEntity3.setCity("Warsaw");
        adEntity3.setCreatedDateTime(LocalDateTime.of(LocalDate.of(2020, 8, 21), LocalTime.of(20, 18)));
        adEntity3.setModifiedDateTime(LocalDateTime.now());

        return List.of(adEntity1,adEntity2,adEntity3);
    }

    private List<ConversationEntity> setUpConversationEntityList() {
        ConversationEntity conversationEntity1 = new ConversationEntity();
        conversationEntity1.setId(1L);
        conversationEntity1.setTitle("The LOTR book");
        conversationEntity1.setRecipientId(1L);
        conversationEntity1.setSenderId(2L);
        conversationEntity1.setMessageList(setUpMessageEntityList());

        return List.of(conversationEntity1);
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

        return List.of(messageEntity1,messageEntity2);
    }

    @Test
    public void shouldMapToDtoWithNestedObjects(){
        //given
        //when
        UserDto userDto = userMapper.userEntityToUserDto(user);
        //then
        assertThat(userDto).isNotNull();
        assertThat(userDto).isInstanceOf(UserDto.class);
        assertThat(userDto).isNotInstanceOf(UserEntity.class);
        assertThat(userDto.getAdList().get(0)).isInstanceOf(AdDto.class);
        assertThat(userDto.getAdList().get(0).getDescription()).isEqualTo(user.getAdEntityList().get(0).getDescription());
        assertThat(userDto.getConversationDtoList().get(0)).isInstanceOf(ConversationDto.class);
        assertThat(userDto.getConversationDtoList().get(0).getSenderId()).isEqualTo(user.getConversationEntityList().get(0).getSenderId());
        assertThat(userDto.getConversationDtoList().get(0).getMessageDtoList().get(0)).isInstanceOf(MessageDto.class);
        assertThat(userDto.getConversationDtoList().get(0).getMessageDtoList().get(0).getDateTime())
                .isEqualTo(user.getConversationEntityList().get(0).getMessageList().get(0).getDateTime());
    }

    @Test
    public void shouldMapToEntityWithNestedObjects(){
        //given
        UserDto userDto = userMapper.userEntityToUserDto(user);  // Can I use mapper on user value to prepare object for test? or it's too dangerous?
        //when
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        //then
        assertThat(userEntity).isNotNull();
        assertThat(userEntity).isInstanceOf(UserEntity.class);
        assertThat(userEntity).isNotInstanceOf(UserDto.class);
        assertThat(userEntity.getAdEntityList().get(0)).isInstanceOf(AdEntity.class);
        assertThat(userEntity.getAdEntityList().get(0).getDescription()).isEqualTo(userDto.getAdList().get(0).getDescription());
        assertThat(userEntity.getConversationEntityList().get(0)).isInstanceOf(ConversationEntity.class);
        assertThat(userEntity.getConversationEntityList().get(0).getSenderId()).isEqualTo(userDto.getConversationDtoList().get(0).getSenderId());
        assertThat(userEntity.getConversationEntityList().get(0).getMessageList().get(0)).isInstanceOf(MessageEntity.class);
        assertThat(userEntity.getConversationEntityList().get(0).getMessageList().get(0).getDateTime())
                .isEqualTo(userDto.getConversationDtoList().get(0).getMessageDtoList().get(0).getDateTime());
    }


}