package com.myTrade.integrationTests.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.UserUtility;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "brad@brad.brad")
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @CsvFileSource(resources = "/registrationRequest.csv", numLinesToSkip = 1)
    @WithAnonymousUser
    public void whenProperUserEntityIsProvided_thenShouldReturnStatus201(String username, String email, String password) {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest(username, email, password);
        //when & then
        try {
            mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registrationRequest)))
                    .andDo(print())
                    .andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(userRepository.getByUsername(username));
        assertThat(optionalUserEntity).isNotNull();
        assertThat(optionalUserEntity.get().getId()).isNotNull();
        assertThat(optionalUserEntity.get().getUsername()).isNotNull();
        assertThat(optionalUserEntity.get().getRole()).isNotNull();
        assertThat(optionalUserEntity.get().getPassword()).isNotNull();
    }

    @ParameterizedTest
    @WithAnonymousUser
    @MethodSource("invalidUserRegistrationRequests")
    public void whenInvalidRegistrationRequestIsProvided_thenShouldReturnStatus406(RegistrationRequest registrationRequest) {
        //given
        //when & then
        try {
            mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(registrationRequest)))
                    .andDo(print())
                    .andExpect(status().is(406));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    @ValueSource(longs = {4,5,6,7,8})
    public void whenProperAdIdIsProvided_thenShouldAddAdToUserFavouriteAdListAndReturnStatus200(Long adId) {
        //given
        //when & then
        try {
            mockMvc.perform(patch("/user/patch/favourite/add/{id}", String.valueOf(adId)))
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AdEntity> userFavouriteAdEntityList = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getFavouriteAdEntityList();
        AdEntity favouriteAd = adRepository.getById(adId);
        assertThat(userFavouriteAdEntityList).contains(favouriteAd);
    }

    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6,7,8})
    public void whenProperAdIdIsProvided_thenShouldRemoveAdFromUserFavouriteAdListAndReturnStatus200(Long adId) {
        //given
        //when && then
        try {
            mockMvc.perform(patch("/user/patch/favourite/remove/{id}", String.valueOf(adId)))
                    .andDo(print())
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AdEntity> userFavouriteAdEntityList = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getFavouriteAdEntityList();
        AdEntity favouriteAd = adRepository.getById(adId);
        assertThat(userFavouriteAdEntityList).doesNotContain(favouriteAd);
    }
}

