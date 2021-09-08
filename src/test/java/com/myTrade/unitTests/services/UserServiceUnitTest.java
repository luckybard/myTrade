package com.myTrade.unitTests.services;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.UserService;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    //how to cover methods in UserService.class?
    @Mock
    private UserRepository userRepository;

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private UserService userService;

    public UserEntity user;

    @BeforeEach
    public void setUpUserEntity() {
        user = new UserEntity();
        user.setUserName("bart");
        user.setPassword("uniquePassword");
        user.setEmail("bart@bart.com");
        user.setBirthDate(LocalDate.of(1990,8,10));
        user.setAdEntityList(setUpAdEntityList());
        user.setConversationEntityList(setUpConversationEntityList());
    }

    public List<AdEntity> setUpAdEntityList() {
        AdEntity adEntity1 = new AdEntity();
        adEntity1.setId(1L);
        adEntity1.setOwnerId(1L);
        adEntity1.setAdCategory(AdCategory.BOOKS);
        adEntity1.setTitle("The Lord of the rings");
        adEntity1.setImagePath("image/path");
        adEntity1.setDescription("The best book");
        adEntity1.setPrice(40.00);
        adEntity1.setCity("Warsaw");
        adEntity1.setIsActive(Boolean.TRUE);

        AdEntity adEntity2 = new AdEntity();
        adEntity2.setId(2L);
        adEntity2.setOwnerId(1L);
        adEntity2.setAdCategory(AdCategory.FURNITURE);
        adEntity2.setTitle("Antique chair");
        adEntity2.setImagePath("image/path");
        adEntity2.setDescription("Comfortable chair");
        adEntity2.setPrice(60.00);
        adEntity2.setCity("Warsaw");
        adEntity2.setIsActive(Boolean.TRUE);

        AdEntity adEntity3 = new AdEntity();
        adEntity3.setId(3L);
        adEntity3.setOwnerId(1L);
        adEntity3.setAdCategory(AdCategory.CLOTHES);
        adEntity3.setTitle("AC/DC T-shirt");
        adEntity3.setImagePath("image/path");
        adEntity3.setDescription("XL Size");
        adEntity3.setPrice(80.25);
        adEntity3.setCity("Warsaw");
        adEntity3.setIsActive(Boolean.FALSE);

        return List.of(adEntity1,adEntity2,adEntity3);
    }

    public List<ConversationEntity> setUpConversationEntityList() {
        ConversationEntity conversationEntity1 = new ConversationEntity();
        conversationEntity1.setId(1L);
        conversationEntity1.setTitle("The LOTR book");
        conversationEntity1.setRecipientId(1L);
        conversationEntity1.setSenderId(2L);
        conversationEntity1.setMessageList(setUpMessageEntityList());

        return List.of(conversationEntity1);
    }

    public List<MessageEntity> setUpMessageEntityList() {
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
    public void whenUserEntityIsProvided_thenRetrievedEntityShouldBeSavedIntoDatabase() {
        //given
        //when
        userService.saveUserEntity(user);
        //then
        verify(userRepository).save(user);
    }

    @Test
    public void whenUserNameIsProvided_thenRetrievedEmailIsCorrect() {
        //given
        given(userRepository.findByUserName(user.getUserName())).willReturn(user);
        String expected = "bart@bart.com";
        //when
        String resultEmail = userRepository.findByUserName(user.getUserName()).getEmail();

        //then
        assertThat(resultEmail).isEqualTo(expected);
    }

    @Test
    public void whenUserNameIsProvided_thenRetrievedNumberOfAdsIsCorrect() {
        //given
        given(userRepository.findByUserName(user.getUserName())).willReturn(user);
        int expected = 3;
        //when
        int result = userService.findUserAdEntityList(user.getUserName()).stream().collect(Collectors.toList()).size();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenUserNameIsProvided_thenRetrievedNumberOfConversationsIsCorrect() {
        //given
        given(userRepository.findByUserName(user.getUserName())).willReturn(user);
        int expected = 1;
        //when
        int result = userService.findUserConversationEntityList(user.getUserName()).size();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenUserNameIsProvided_thenUserShouldBeRemovedFromDataBase() {
        //given
        given(userRepository.findByUserName(user.getUserName())).willReturn(user);
        //when
        userService.deleteUser(user.getUserName());
        //then
        verify(userRepository).delete(user);

    }

    @Test
    public void whenUserNameAndAdIdIsProvided_shouldDeleteAdFromAdList() {
        //given
        given(userRepository.findByUserName(user.getUserName())).willReturn(user);
        int expectedListSize = 2;
        //when
        userService.deleteAdFromAdList(user.getUserName(), 1L);
        int result = userRepository.findByUserName(user.getUserName()).getAdEntityList().size();
        //then
        assertThat(result).isEqualTo(expectedListSize);
        verify(adRepository).deleteById(1L);
    }


}