package com.myTrade.mappersImpl;

import com.myTrade.dto.UserDto;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappers.UserMapper;
import com.myTrade.utility.pojo.RegistrationRequest;

public class UserMapperImpl implements UserMapper {
    private final AdMapperImpl adMapper;

    public UserMapperImpl() {
        this.adMapper = new AdMapperImpl();
    }

    @Override
    public UserDto userEntityToUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        userDto.setAdList(adMapper.adEntityListToAdDtoList(userEntity.getAdEntityList()));

        return userDto;
    }

    @Override
    public UserEntity userDtoToUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());
        userEntity.setAdEntityList(adMapper.adDtoListToAdEntityList(userDto.getAdList()));


        return userEntity;
    }

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
