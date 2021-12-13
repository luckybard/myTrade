package com.myTrade.unitTests.services;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.exceptions.UserValidationException;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.UserService;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import static com.myTrade.utility.TestUtility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    private UserEntity user = new UserEntity();

    @BeforeEach
    public void beforeEach() {
        setUpDefaultUserEntity(user);
        setUpSecurityContext(user);
    }

    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources = "/registrationRequest.csv", numLinesToSkip = 1)
    public void whenValidRegistrationRequestIsProvided_thenUserEntityShouldBeSaved(String username, String email, String password) {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest(username, email, password);
        //when
        userService.saveUserEntityByRegistrationRequest(registrationRequest);
        //then
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        assertThat(userEntityArgumentCaptor.getValue().getId()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidUserRegistrationRequests")
    public void whenInvalidRegistrationRequestIsProvided_thenShouldReturnStatus406(RegistrationRequest registrationRequest) {
        //given
        //when & then
        assertThrows(UserValidationException.class, () -> {
            userService.saveUserEntityByRegistrationRequest(registrationRequest);
        });
    }

    private static Stream<Arguments> invalidUserRegistrationRequests() {
        return Stream.of(
                Arguments.of(new RegistrationRequest("brad@brad", "brad@brad", "brad@brad")),
                Arguments.of(new RegistrationRequest("            ", "brad@brad", "brad@brad")),
                Arguments.of(new RegistrationRequest("brad@brad", "12345", "brad@brad")),
                Arguments.of(new RegistrationRequest("br", "brad@brad.com", "brad@brad")),
                Arguments.of(new RegistrationRequest("br", "            ", "brad@brad")),
                Arguments.of(new RegistrationRequest("        ", "      ", "        ")),
                Arguments.of(new RegistrationRequest("", "", ""))
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv", numLinesToSkip = 1)
    public void whenProperAdIdIsProvided_thenShouldAddAdToUserFavouriteAdListAndReturnStatus200(Long id,
                                                                                                AdCategory adCategory,
                                                                                                String title,
                                                                                                String description,
                                                                                                City city,
                                                                                                Double price,
                                                                                                String ownerUsername,
                                                                                                Long countView,
                                                                                                Boolean isActive,
                                                                                                LocalDate createdDate,
                                                                                                LocalDate modifiedDate,
                                                                                                LocalDate refreshDate,
                                                                                                LocalDate expirationHighlightDate) {
        //given
        AdEntity adEntity = AdEntity.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .countView(countView)
                .createdDate(createdDate)
                .description(description)
                .expirationHighlightDate(expirationHighlightDate)
                .isActive(isActive)
                .modifiedDate(modifiedDate)
                .ownerUsername(ownerUsername)
                .price(price)
                .refreshDate(refreshDate)
                .title(title)
                .build();
        UserEntity userEntity = UserEntity.builder()
                .favouriteAdEntityList(new ArrayList<>())
                .username(user.getUsername())
                .build();
        given(userRepository.getByUsername(user.getUsername())).willReturn(userEntity);
        given(adRepository.getById(id)).willReturn(adEntity);
        //when
        userService.addAdFromUserFavouriteAdListById(id);
        //then
        verify(adRepository).getById(id);
        verify(userRepository).getByUsername(user.getUsername());
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        assertThat(userEntityArgumentCaptor.getValue().getFavouriteAdEntityList()).contains(adEntity);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adId.csv", numLinesToSkip = 1)
    public void whenProperAdIdIsProvided_thenShouldRemoveAdFromUserFavouriteAdListAndReturnStatus200(Long adId) {
        //given
        AdEntity adEntity = getAdEntity();
        UserEntity userEntity = UserEntity.builder()
                .favouriteAdEntityList(getAdEntityList())
                .username(user.getUsername())
                .build();
        given(userRepository.getByUsername(user.getUsername())).willReturn(userEntity);
        given(adRepository.getById(adId)).willReturn(adEntity);
        //when
        userService.removeAdFromUserFavouriteAdListById(adId);
        //then
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        assertThat(userEntityArgumentCaptor.getValue().getFavouriteAdEntityList()).doesNotContain(adEntity);
    }

    @Test
    public void whenUserIsLoggedIn_shouldRetrievedUserHighlightPoints(){
        //given
        Integer expectedUserHighlightPoints = user.getHighlightPoints();
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        Integer actualUserHighlightPoints = userService.fetchUserHighlightPoints();
        //then
        assertThat(actualUserHighlightPoints).isEqualTo(expectedUserHighlightPoints);
    }
}