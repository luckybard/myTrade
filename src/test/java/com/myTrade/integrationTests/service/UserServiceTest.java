package com.myTrade.integrationTests.service;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.exceptions.UserValidationException;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.UserService;
import com.myTrade.utility.UserUtility;
import com.myTrade.utility.pojo.RegistrationRequest;
import com.myTrade.utility.pojo.UserRole;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "brad@brad.brad")
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

    @ParameterizedTest
    @WithAnonymousUser
    @CsvFileSource(resources = "/registrationRequest.csv", numLinesToSkip = 1)
    public void whenValidUserRegistrationRequestIsProvided_thenUserEntityShouldBeSaved(String username, String email, String password) {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest(username, email, password);
        ///when
        userService.saveUserEntityByRegistrationRequest(registrationRequest);
        UserEntity savedEntity = userRepository.getByUsername(username);
        //then
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getUsername()).isEqualTo(username);
        assertThat(savedEntity.getRole()).isEqualTo(UserRole.USER);
    }

    @ParameterizedTest
    @WithAnonymousUser
    @MethodSource("invalidUserRegistrationRequests")
    public void whenInvalidUserRegistrationRequestIsProvided_thenShouldThrowUserValidationException(RegistrationRequest registrationRequest){
        //given
        //when & then
        assertThrows(UserValidationException.class, () -> {
            userService.saveUserEntityByRegistrationRequest(registrationRequest);
        });
    }

    private static Stream<Arguments> invalidUserRegistrationRequests() {
        return Stream.of(
                Arguments.of(new RegistrationRequest("brad@brad","brad@brad", "brad@brad")),
                Arguments.of(new RegistrationRequest("            ","brad@brad", "brad@brad")),
                Arguments.of(new RegistrationRequest("brad@brad","12345", "brad@brad")),
                Arguments.of(new RegistrationRequest("br","brad@brad.com","brad@brad")),
                Arguments.of(new RegistrationRequest("br","            ","brad@brad")),
                Arguments.of(new RegistrationRequest("        ","      ","        ")),
                Arguments.of(new RegistrationRequest("","",""))
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {4,5,6,7,8})
    public void whenValidAdIdIsProvided_thenAdShouldBeAddedToUserFavouriteAdEntityList(Long adId) {
        //given
        AdEntity expectedAdEntity = adRepository.getById(adId);
        //when
        userService.addAdFromUserFavouriteAdListById(adId);
        List<AdEntity> actualUserFavouriteAddEntityList = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getFavouriteAdEntityList();
        //then
        assertThat(actualUserFavouriteAddEntityList).contains(expectedAdEntity);
    }

    @ParameterizedTest
    @ValueSource(longs = {4,5,6,7,8})
    public void whenValidAdIdIsProvided_thenAdShouldBeRemovedFromUserFavouriteAdEntityList(Long adId) {
        //given
        AdEntity notExpectedAdEntity = adRepository.getById(adId);
        //when
        userService.removeAdFromUserFavouriteAdListById(adId);
        List<AdEntity> actualUserFavouriteAddEntityList = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getFavouriteAdEntityList();
        //then
        assertThat(actualUserFavouriteAddEntityList).doesNotContain(notExpectedAdEntity);
    }
}
