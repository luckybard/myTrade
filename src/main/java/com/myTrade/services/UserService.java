package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private AdRepository adRepository;
    private AdMapperImpl adMapper = new AdMapperImpl();
    private PasswordEncoder passwordEncoder;
    private AdService adService;

    @Autowired
    public UserService(UserRepository userRepository, AdRepository adRepository, PasswordEncoder passwordEncoder, AdService adService){
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.passwordEncoder = passwordEncoder;
        this.adService = adService;
    }

    public void saveUserEntity(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public Page<AdDto> findUserAdEntityList(Integer pageNumber, Integer pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<AdEntity> adEntityPage = adRepository.findByOwnerUsername(username,PageRequest.of(pageNumber, pageSize, Sort.by("created_date_time").descending()));
        return adEntityPage.map(adEntity -> adMapper.adEntityToAdDto(adEntity));
    }

    public void deleteUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.delete(userRepository.findByUsername(username));
    }

    public void deductHighlightPoint() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(username);
        int userHighlightPoints = userEntity.getHighlightPoint();
        userEntity.setHighlightPoint(--userHighlightPoints);
        userRepository.save(userEntity);
    }

    public void addOrRemoveFromFavouriteList(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get(); //TODO:[Q] Better Optional?
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        List<AdEntity> adEntityList = user.getFavouriteAdEntityList().stream().collect(Collectors.toList());
        if(adEntityList.contains(adEntity)){
            adEntityList.remove(adEntity);
        } else {
            adEntityList.add(adEntity);
        }
        user.setFavouriteAdEntityList(adEntityList);
        userRepository.save(user);
    }

    public Page<AdDto> findUserFavouriteAdEntityList(Integer pageNumber, Integer pageSize) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByUsername(username).getId();
        Page<AdEntity> adEntityPage =  adRepository.findUserFavouriteAdsByUserId(userId, PageRequest.of(pageNumber, pageSize)); //TODO:[Q] How to sort by the order of adding
        return adService.setIsUserFavouriteAds(adEntityPage).map(adEntity -> adMapper.adEntityToAdDto(adEntity));
    }


    public Page<AdDto> findUserAdEntityList(String username, Integer pageNumber, Integer pageSize) {
        Page<AdEntity> adEntityPage = adRepository.findByOwnerUsername(username,PageRequest.of(pageNumber, pageSize, Sort.by("created_date_time").descending()));
        return adService.setIsUserFavouriteAds(adEntityPage).map(adEntity -> adMapper.adEntityToAdDto(adEntity));
    }
}

