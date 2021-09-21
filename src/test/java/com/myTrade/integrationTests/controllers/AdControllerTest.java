package com.myTrade.integrationTests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private AdMapperImpl adMapper = new AdMapperImpl();
    private AdEntity ad = new AdEntity();

    @BeforeEach
    public void setUpAd() {
        ad.setId(1L);
        ad.setOwnerUsername("bart");
        ad.setAdCategory(AdCategory.BOOKS);
        ad.setTitle("The Lord of the rings");
        ad.setImagePath("myTrade.com/image");
        ad.setDescription("The best book ever!");
        ad.setPrice(100.00);
        ad.setCity("Warsaw");
        ad.setIsActive(Boolean.FALSE);
    }

    @Test
    @WithMockUser(authorities = "ad:read")
    public void whenProperAdIdIsProvided_thenRetrieved200() {
        //given
        given(adRepository.getById(1L)).willReturn(ad);
        Long adId = 1L;
        //when & then
        try {
            mockMvc.perform(get("/ad/search/{id}", String.valueOf(adId))).andExpect(status().is(200))
                    .andExpect(content().json(objectMapper.writeValueAsString(ad)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(authorities = "ad:write")
    public void whenProperAdDtoIsProvided_thenRetrieved201() {
        //given
        AdDto adDto = adMapper.adEntityToAdDto(ad);
        //when & then
        try {
            mockMvc.perform(post("/ad/create").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adDto))).andExpect(status().is(201));
//            verify(adRepository).save(adMapper.adDtoAdEntity(adDto)); TODO: [Q]Is it possible to verify this object? (2 fields are modified before being saved (LocalDateTime.now())
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(authorities = "ad:write")
    public void whenProperAdDtoForEditIsProvided_thenRetrieved200() {
        //given
        Long adId = 1L;
        AdDto editedAdDto = adMapper.adEntityToAdDto(ad);
        editedAdDto.setTitle("New");
        editedAdDto.setCity("New");
        editedAdDto.setDescription("New");
        editedAdDto.setAdCategory(AdCategory.OTHER);
        //when & then
        try {
            mockMvc.perform(patch("/ad/edit/{id}", String.valueOf(adId)).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(editedAdDto))).andExpect(status().is(200));
            //TODO: [Q] Look up, same question.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//                                              !- For learning purpose -!
 /*   @Test
    @WithMockUser(authorities = "ad:write")
    public void whenProperPathVariableAndNewTitleIsProvided_thenRetrieved200AndTitleShouldBeChanged() {
        //given
        String newTitle = "newTitle";
        //when
        //then
        try {
            mockMvc.perform(patch("/ad/{id}/edit").contentType(MediaType.APPLICATION_JSON)
                    .param("id", "1").content(objectMapper.writeValueAsString(newTitle))).andExpect(status().is(200));
            assertThat(adRepository.findById(1L).get().getTitle()).isEqualTo("something");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @WithMockUser(authorities = "ad:write")
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
    @WithMockUser(authorities = "ad:write")
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
    @WithMockUser(authorities = "ad:write")
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
    @WithMockUser(authorities = "ad:write")
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
    @WithMockUser(authorities = "ad:write")
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
    @WithMockUser(authorities = "ad:write")
    public void whenProperPathVariableAndNewStatusIsProvided_thenRetrieved200AndStatusShouldBeChanged() {
        //given
        Boolean newStatus = true;
        long adId = 1;

        try {
            mockMvc.perform(patch("/ad/{id}/editStatus").contentType(MediaType.APPLICATION_JSON)
                    .param("id", String.valueOf(adId)).content(objectMapper.writeValueAsString(newStatus))).andExpect(status().is(200));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}*/

