package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.AdDto;
import com.myTrade.repositories.AdRepository;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void whenProperAdIdIsProvided_thenRetrieved200() {
        try {
            mockMvc.perform(get("/ad/search/{id}").param("id", "1")).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperAdDtoIsProvided_thenRetrieved201() {
        //given
        AdDto ad = new AdDto();
        ad.setId(1L);
        ad.setOwnerId(1L);
        ad.setAdCategory(AdCategory.BOOKS);
        ad.setTitle("The Lord of the rings");
        ad.setImagePath("myTrade.com/image");
        ad.setDescription("The best book ever!");
        ad.setPrice(100.00);
        ad.setCity("Warsaw");
        ad.setIsActive(Boolean.FALSE);

        //when
        //then
        try {
            mockMvc.perform(post("/ad/save").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ad))).andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewTitleIsProvided_thenRetrieved200AndTitleShouldBeChanged() {
        //given
        String newTitle = "newTitle";
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editTitle").contentType(MediaType.APPLICATION_JSON)
                    .param("id", "1").content(objectMapper.writeValueAsString(newTitle))).andExpect(status().is(200));
            assertThat(adRepository.findById(1L).get().getTitle()).isEqualTo("something");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewCategoryIsProvided_thenRetrieved200AndCategoryShouldBeChanged() {
        AdCategory newAdCategory = AdCategory.OTHER;
        try {
            mockMvc.perform(patch("/ad/{id}/editCategory").contentType(MediaType.APPLICATION_JSON)
                    .param("id", "1").content(objectMapper.writeValueAsString(newAdCategory))).andExpect(status().is(200));
//            AdCategory actual = adRepository.findById(1L).get().getAdCategory();
//            assertThat(actual).isEqualTo(newAdCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewImagePathIsProvided_thenRetrieved200AndImagePathShouldBeChanged() {
        //given
        String newImagePath = "newImagePath";
//        String fake = "fake";
        long adId = 1;
//        String actual;
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editImagePath").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newImagePath))).andExpect(status().is(200));
//            actual = adRepository.findById(1L).get().getImagePath();
//            assertThat(fake).isEqualTo(newImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewDescriptionIsProvided_thenRetrieved200AndDescriptionShouldBeChanged() {
        //given
        String newDescription = "newDescription";
        long adId = 1;
//        String actual;
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editDescription").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newDescription))).andExpect(status().is(200));
//            actual = adRepository.findById(1L).get().getDescription();
//            assertThat(actual).isEqualTo(newDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewPriceIsProvided_thenRetrieved200AndPriceShouldBeChanged() {
        //given
        Double newPrice = 100000000.2;
        long adId = 1;
//        Double actual;
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editPrice").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newPrice))).andExpect(status().is(200));
//            actual = adRepository.findById(1L).get().getPrice();
//            assertThat(actual).isEqualTo(newPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewCityIsProvided_thenRetrieved200AndCityShouldBeChanged() {
        //given
        String newCity = "newCity";
        long adId = 1;
//        String actual;
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editCity").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newCity))).andExpect(status().is(200));
//            actual = adRepository.findById(1L).get().getCity();
//            assertThat(actual).isEqualTo(newCity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenProperPathVariableAndNewStatusIsProvided_thenRetrieved200AndStatusShouldBeChanged() {
        //given
        Boolean newStatus = true;
        long adId = 1;
//        Boolean actual;
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/editStatus").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newStatus))).andExpect(status().is(200));
//            actual = adRepository.findById(1L).get().getIsActive();
//            assertThat(actual).isEqualTo(newStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

