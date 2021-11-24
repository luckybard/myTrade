package com.myTrade.mappers;

import com.myTrade.dto.UserDto;
import com.myTrade.entities.UserEntity;
import com.myTrade.utility.pojo.RegistrationRequest;

public interface UserMapper {
    UserDto userEntityToUserDto(UserEntity userEntity);
    UserEntity userDtoToUserEntity(UserDto userDto);
    UserEntity registrationRequestToUserEntity(RegistrationRequest registrationRequest);
}
