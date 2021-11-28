package com.myTrade.services;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.exceptions.UserValidationException;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.pojo.RegistrationRequest;
import com.myTrade.validators.registrationRequest.RegistrationRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.myTrade.utility.SecurityUtility.encodeUserPassword;
import static com.myTrade.utility.UserUtility.getUsernameFromContext;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final UserMapperImpl userMapper = new UserMapperImpl();
    private final RegistrationRequestValidator requestValidator = new RegistrationRequestValidator();

    @Autowired
    public UserService(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    public void addAdFromUserFavouriteAdListById(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        UserEntity user = userRepository.findByUsername(getUsernameFromContext());
        List<AdEntity> adEntityList = new ArrayList<>(user.getFavouriteAdEntityList());
        adEntityList.add(adEntity);
        user.setFavouriteAdEntityList(adEntityList);
        userRepository.save(user);
    }

    public void removeAdFromUserFavouriteAdListById(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        UserEntity user = userRepository.findByUsername(getUsernameFromContext());
        List<AdEntity> adEntityList = new ArrayList<>(user.getFavouriteAdEntityList());
        adEntityList.remove(adEntity);
        user.setFavouriteAdEntityList(adEntityList);
        userRepository.save(user);
    }


    public void saveUserEntityByRegistrationRequest(RegistrationRequest registrationRequest) {
        if (requestValidator.test(registrationRequest)) {
            UserEntity userEntity = userMapper.registrationRequestToUserEntity(registrationRequest);
            encodeUserPassword(userEntity);
            userRepository.save(userEntity);
        } else throw new UserValidationException();
    }
}

