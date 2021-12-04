package com.myTrade.unitTests.mappers;


import com.myTrade.entities.UserEntity;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperImplTest {

    UserMapperImpl userMapper = new UserMapperImpl();

    @ParameterizedTest
    @CsvFileSource(resources = "/registrationRequest.csv", numLinesToSkip = 1)
    public void shouldMapRegistrationRequestToUserEntity(String username, String email, String password){
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest(username,email,password);
        //when
        UserEntity userEntity = userMapper.registrationRequestToUserEntity(registrationRequest);
        //then
        assertThat(userEntity).isNotNull();
        assertThat(userEntity).isInstanceOf(UserEntity.class);
        assertThat(userEntity.getUsername()).isEqualTo(registrationRequest.getUsername());
        assertThat(userEntity.getEmail()).isEqualTo(registrationRequest.getEmail());
        assertThat(userEntity.getPassword()).isEqualTo(registrationRequest.getPassword());
    }
}