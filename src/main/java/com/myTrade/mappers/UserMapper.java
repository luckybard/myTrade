package com.myTrade.mappers;

import com.myTrade.entities.UserEntity;
import com.myTrade.utility.pojo.RegistrationRequest;

public interface UserMapper {
    UserEntity registrationRequestToUserEntity(RegistrationRequest registrationRequest);
}
