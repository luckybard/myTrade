package com.myTrade.mappersImpl;

import com.myTrade.entities.UserEntity;
import com.myTrade.mappers.UserMapper;
import com.myTrade.utility.pojo.RegistrationRequest;

public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity registrationRequestToUserEntity(RegistrationRequest registrationRequest) {
        if (registrationRequest == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(registrationRequest.getUsername());
        userEntity.setPassword(registrationRequest.getPassword());
        userEntity.setEmail(registrationRequest.getEmail());

        return userEntity;
    }
}
