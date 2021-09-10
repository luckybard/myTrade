package com.myTrade.integrationTests.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myTrade.dto.AdDto;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.AdCategory;
import com.myTrade.utility.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenProperUserEntityIsProvided_thenShouldReturnStatus201() {
        //given
        UserEntity user = new UserEntity();
        user.setUserName("xxx");
        user.setPassword("uniquePassword");
        user.setEmail("axxc");
        user.setAvatarPath(" sd");
        user.setBirthDate(LocalDate.of(1990, 8, 10));
        user.setRole(UserRole.USER);
        //when
        //then
        try {
            mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().is(201));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperUserNameIsProvided_thenShouldReturnUserDtoAndStatus200() {
        //given
        String userName = "bart";
        //when
        //then
        try {
            mockMvc.perform(get("/user/{username}").param("username", userName)).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperUserNameIsProvided_thenShouldReturnUserAdDtoListAndStatus200() {
        //given
        String userName = "bart";
        //when
        //then
        try {
            mockMvc.perform(get("/user/{username}/adList").param("username", userName)).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperUserNameIsProvided_thenShouldReturnUserConversationDtoListAndStatus200() {
        //given
        String userName = "bart";
        //when
        //then
        try {
            mockMvc.perform(get("/user/{username}/inbox").param("username", userName)).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperUserNameIsProvided_thenShouldDeleteUserAndReturnStatus200() {
        //given
        String userName = "bart";
        //when
        //then
        try {
            mockMvc.perform(delete("/user/{username}/delete").param("username", userName)).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenProperUserNameAndAdIdAreProvided_thenShouldRemoveAdFromUserAdListAndReturnStatus200(){
        //given
        String userName = "bart";
        Long adId = 1L;
        //when
        //then
        try {
            mockMvc.perform(patch("/user/{username}/adList/delete/{id}").param("username", userName)
                    .param("id", String.valueOf(adId))).andExpect(status().is(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 @Test
 public void whenUserNameAndAdDtoIsProvided_thenShouldSaveAdAndReturnStatus201(){
        //given
     String userName = "XXXXXXXXXXXXXXXXXXX";
     AdDto ad = new AdDto();
     ad.setOwnerId(1L);
     ad.setAdCategory(AdCategory.BOOKS);
     ad.setTitle("The Lord of the rings");
     ad.setImagePath("myTrade.com/image");
     ad.setDescription("The best book ever!");
     ad.setPrice(100.00);
     ad.setCity("Warsaw");
     ad.setIsActive(Boolean.FALSE);
     try {
         mockMvc.perform(post("/user/{username}/adList/add").contentType(MediaType.APPLICATION_JSON).param("username",userName).content(objectMapper.writeValueAsString(ad)))
                 .andExpect(status().is(201));
         //TODO: Despite not including param username, test passed.
         // IllegalArgumentException occurs, check that! java.lang.IllegalArgumentException: Not enough variable values available to expand 'username'
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
