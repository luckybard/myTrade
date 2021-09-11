package com.myTrade.mappers;

import com.myTrade.dto.UserDto;
import com.myTrade.entities.UserEntity;


import java.util.List;

public interface UserMapper {
    UserDto userEntityToUserDto(UserEntity userEntity);

    List<UserDto> userEntityListToUserDtoList(List<UserEntity> userEntityList);

    UserEntity userDtoToUserEntity(UserDto userDto);

    List<UserEntity> userDtoListToUserEntityList(List<UserDto> userDtoList);
}
