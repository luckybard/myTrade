package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.AdEditDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.AdUtility;
import com.myTrade.utility.UserUtility;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.myTrade.utility.TestUtility.ONE_ADDITIONAL_AD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "brad@brad.brad", authorities = {"ad:write","ad:read"})
public class AdControllerTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdControllerTest(MockMvc mockMvc, UserRepository userRepository, AdRepository adRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.objectMapper = objectMapper;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEditDtoWithoutId.csv", numLinesToSkip = 1)
    public void whenAdEditDtoIsProvided_thenAdShouldBeSavedAndRetrieved201(AdCategory adCategory,
                                                                           String title,
                                                                           String description,
                                                                           City city,
                                                                           Double price) {
        //given
        AdEditDto adEditDto = AdEditDto.builder()
                .adCategory(adCategory)
                .city(city)
                .description(description)
                .title(title)
                .price(price)
                .build();
        int expectedUserAdListSize = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().size() + ONE_ADDITIONAL_AD;
        //when & then
        try {
            mockMvc.perform(post("/ad")
                            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(adEditDto)))
                    .andDo(print())
                    .andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int actualUserAdListSize = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().size();
        assertThat(actualUserAdListSize).isEqualTo(expectedUserAdListSize);
    }

    @Test
    public void whenPageSizeIsProvided_thenShouldFetchRandomAdDtoPageAndRetrieved200() {
        //given
        //when && then
        try {
            mockMvc.perform(get("/ad/random?pageNumber=0&pageSize=2"))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].description").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].price").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].adCategory").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperAdIdIsProvided_thenAdEditDtoShouldBeFetchByIdAndRetrieved200() {
        //given
        AdEntity userAdEntity = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().stream().findFirst().get();
        //when & then
        try {
            mockMvc.perform(get("/ad/edit/{id}", String.valueOf(userAdEntity.getId())))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.adCategory").value(userAdEntity.getAdCategory().name()))
                    .andExpect(jsonPath("$.city").value(userAdEntity.getCity().name()))
                    .andExpect(jsonPath("$.title").value(userAdEntity.getTitle()))
                    .andExpect(jsonPath("$.price").value(userAdEntity.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adId.csv", numLinesToSkip = 1)
    public void whenProperAdIdIsProvided_thenAdDtoShouldBeFetchByIdAndRetrieved200(Long adId) {
        //given
        //when & then
        try {
            mockMvc.perform(get("/ad/{id}", String.valueOf(adId)))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.adCategory").isNotEmpty())
                    .andExpect(jsonPath("$.city").isNotEmpty())
                    .andExpect(jsonPath("$.title").isNotEmpty())
                    .andExpect(jsonPath("$.price").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenPageNumberAndPageSizeIsProvided_thenShouldFetchAdOwnerDtoPageAndRetrieved200() {
        //given
        //when & then
        try {
            mockMvc.perform(get("/ad/adList?pageNumber=0&pageSize=2"))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].description").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].price").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].adCategory").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenUsernamePageNumberAndPageSizeIsProvided_thenShouldFetchOwnerAdDtoPageByUsernameAndRetrieved200() {
        //given
        String username = "john@john.john";
        //when && then
        try {
            mockMvc.perform(get("/ad/adList/{username}?pageNumber=0&pageSize=2", username))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].description").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].price").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].adCategory").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test

    public void whenPageNumberAndPageSizeIsProvided_thenShouldFetchUserFavouriteAdDtoPageAndRetrieved200() {
        //given
        //when && then
        try {
            mockMvc.perform(get("/ad/favourite/adList?pageNumber=0&pageSize=2"))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect((content().contentType(MediaType.APPLICATION_JSON)))
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].description").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].city").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].price").isNotEmpty())
                    .andExpect(jsonPath("$.content[*].adCategory").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenValidAdEditDtoIsProvided_thenNewValuesShouldBeSetForAdEntityAndRetrieved200() {
        //given
        Long userAdEntityId = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().stream()
                .findFirst()
                .get().getId();
        String expectedTitle = "title,title";
        AdCategory expectedAdCategory = AdCategory.OTHER;
        City expectedCity = City.EVERYWHERE;
        String expectedDescription = "description,description,description,description,description,description";
        Double expectedPrice = 1D;
        AdEditDto adEditDto = AdEditDto.builder().id(userAdEntityId)
                .adCategory(expectedAdCategory)
                .city(expectedCity)
                .description(expectedDescription)
                .title(expectedTitle)
                .price(expectedPrice)
                .build();
        //when && then
        try {
            mockMvc.perform(put("/ad").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(adEditDto)))
                    .andDo(print())
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdEntity actualAdEntity = adRepository.getById(userAdEntityId);
        assertThat(actualAdEntity.getTitle()).isEqualTo(expectedTitle);
        assertThat(actualAdEntity.getDescription()).isEqualTo(expectedDescription);
        assertThat(actualAdEntity.getAdCategory()).isEqualTo(expectedAdCategory);
        assertThat(actualAdEntity.getCity()).isEqualTo(expectedCity);
        assertThat(actualAdEntity.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenProperAdIdIsProvided_thenNewRefreshDateShouldBeSetAndRetrieved200() {
        //given
        LocalDate expectedDate = LocalDate.now();
        AdEntity userAdEntity = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().stream()
                .findFirst()
                .get();
        //when & then
        try {
            mockMvc.perform(patch("/ad/refresh/{id}", String.valueOf(userAdEntity.getId())))
                    .andDo(print())
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalDate actualDate = adRepository.getById(userAdEntity.getId()).getRefreshDate();
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void whenProperAdIdIsProvided_thenNewHighlightDateShouldBeSetAndRetrieved200() {
        //given
        LocalDate expectedDate = LocalDate.now().plusDays(AdUtility.AD_HIGHLIGHTING_DURATION_IN_DAYS);
        AdEntity userAdEntity = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().stream()
                .findFirst()
                .get();
        //when & then
        try {
            mockMvc.perform(patch("/ad/highlight/{id}", String.valueOf(userAdEntity.getId())))
                    .andDo(print())
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalDate actualDate = adRepository.getById(userAdEntity.getId()).getExpirationHighlightDate();
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void whenProperAdIdIsProvided_thenAdStatusShouldBeChangeAndRetrieved200() {
        //given
        AdEntity userAdEntity = userRepository.getByUsername(UserUtility.getUsernameFromContext()).getAdEntityList().stream()
                .findFirst()
                .get();
        Boolean expectedStatus = !userAdEntity.getIsActive();
        //when & then
        try {
            mockMvc.perform(patch("/ad/active/{id}", String.valueOf(userAdEntity.getId())))
                    .andDo(print())
                    .andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean actualStatus = adRepository.getById(userAdEntity.getId()).getIsActive();
        assertThat(actualStatus).isEqualTo(expectedStatus);
    }
}

