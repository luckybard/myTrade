package com.myTrade.mappersImpl;

import com.myTrade.dto.UserDto;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    private AdMapperImpl adMapper;
    private ConversationMapperImpl conversationMapper;

    public UserMapperImpl() {
        this.adMapper = new AdMapperImpl();
        this.conversationMapper = new ConversationMapperImpl();
    }

    @Override
    public UserDto userEntityToUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        userDto.setAvatarPath(userEntity.getAvatarPath());
        userDto.setBirthDate(userEntity.getBirthDate());
        userDto.setAdList(adMapper.adEntityListToAdDtoList(userEntity.getAdEntityList()));
        userDto.setConversationDtoList(conversationMapper.conversationEntityListToConversationDtoList((userEntity.getConversationEntityList())));

        return userDto;
    }

    @Override
    public List<UserDto> userEntityListToUserDtoList(List<UserEntity> userEntityList) {
        if (userEntityList == null) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>(userEntityList.size());
        for (UserEntity userEntity : userEntityList) {
            list.add(userEntityToUserDto(userEntity));
        }

        return list;
    }

    @Override
    public UserEntity userDtoToUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());
        userEntity.setAvatarPath(userDto.getAvatarPath());
        userEntity.setBirthDate(userDto.getBirthDate());
        userEntity.setAdEntityList(adMapper.adDtoListToAdEntityList(userDto.getAdList()));
        userEntity.setConversationEntityList(conversationMapper.conversationDtoListToConversationEntityList(userDto.getConversationDtoList()));

        return userEntity;
    }

    @Override
    public List<UserEntity> userDtoListToUserEntityList(List<UserDto> userDtoList) {
        if (userDtoList == null) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>(userDtoList.size());
        for (UserDto userDto : userDtoList) {
            list.add(userDtoToUserEntity(userDto));
        }

        return list;
    }
}
