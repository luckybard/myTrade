package com.myTrade.integrationTests.service;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.UserRole;
import com.myTrade.services.UserService;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private AdRepository adRepository;

    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.adRepository = adRepository;
    }

    @Test
    @Transactional
    public void whenUserEntityIsProvided_thenEntityShouldBeSaved() {
        //given
        UserEntity user = new UserEntity();
        user.setUserName("xxx");
        user.setPassword("uniquePassword");
        user.setEmail("axxc");
        user.setAvatarPath(" sd");
        user.setBirthDate(LocalDate.of(1990, 8, 10));
        user.setRole(UserRole.USER);
        ///when
        userService.saveUserEntity(user);
        UserEntity expectedEntity = userRepository.findByUserName("xxx");
        //then
        assertThat(expectedEntity).isNotNull();
    }

    @Test
    @Transactional
    public void whenUserNameAndAdEntityIsProvided_thenAdShouldBeSavedIntoUserAdList() {
        //given
        String userName = "bart";
        Long expectedRepositorySize = adRepository.count() + 1;
        AdEntity ad = new AdEntity();
        ad.setOwnerId(1L);
        ad.setAdCategory(AdCategory.BOOKS);
        ad.setTitle("The Lord of the rings");
        ad.setImagePath("image/path");
        ad.setDescription("The best book");
        ad.setPrice(40.00);
        ad.setCity("Warsaw");
        ad.setCreatedDateTime(LocalDateTime.of(LocalDate.of(2020, 8, 21), LocalTime.of(20, 18)));
        ad.setModifiedDateTime(LocalDateTime.now());
        ad.setIsActive(Boolean.TRUE);
        //when
        userService.addAdToAdList(userName, ad);
        int actualUserAdListSize = userRepository.findByUserName(userName).getAdEntityList().size();
        int expectedUserAdListSize = 4;
        Long actualRepositorySize = adRepository.count();
        //then
        assertThat(actualUserAdListSize).isEqualTo(expectedUserAdListSize);
        assertThat(actualRepositorySize).isEqualTo(expectedRepositorySize);
    }

    @Test
    @Transactional
    public void whenUserNameAndAdIdIsProvided_thenAdShouldBeRemovedFromUserAdList() {
        //given
        String userName = "bart";
        int expectedUserAdListSize = userService.findUserAdEntityList(userName).size() - 1;
        Long adId = 1L;
        //when
        userService.deleteAdFromAdList(userName, adId);
        int actualUserAdListSize = userService.findUserAdEntityList(userName).size();
        //then
        assertThat(actualUserAdListSize).isEqualTo(expectedUserAdListSize);
    }

    @Test
    @Transactional
    public void whenUserNameIsProvided_thenShouldRetrievedCorrectAdListSize() {
        //given
        String userName = "bart";
        int expectedAdListSize = 3;
        //when
        int actualAdListSize = userService.findUserAdEntityList(userName).size();
        //then
        assertThat(actualAdListSize).isEqualTo(expectedAdListSize);
    }

    @Test
    @Transactional
    public void whenUserNameIsProvided_thenShouldRetrievedCorrectConversationListSize() {
        //given
        String userName = "bart";
        int expectedUserConversationListSize = 3;
        //when
        int actualUserConversationListSize = userService.findUserConversationEntityList(userName).size();
        //then
        assertThat(actualUserConversationListSize).isEqualTo(expectedUserConversationListSize);
    }

    @Test
    @Transactional
    public void whenUserNameIsProvided_thenUserShouldBeDeleted() {
        //given
        String userName = "kate";
        Long expectedUserRepositorySize = userRepository.count() - 1;
        //when
        userService.deleteUser(userName);
        //then
        assertThat(userRepository.count()).isEqualTo(expectedUserRepositorySize);
    }
}
