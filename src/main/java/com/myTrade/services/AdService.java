package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdService {

    private AdRepository adRepository;
    private final AdMapperImpl adMapper = new AdMapperImpl();

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public AdDto fetchAdDtoById(Long adId){
        return adMapper.adEntityToAdDto(adRepository.getById(adId));
    }

    public void patchAdDto(AdDto adDto){
       AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
       setModifiedDate(adEntity);
       adRepository.save(adEntity);
    }

    public void saveAdDtoWithCreatedAndModifiedDateTime(AdDto adDto){
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setCreatedDate(adEntity);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    private void setModifiedDate(AdEntity adEntity) {
        adEntity.setModifiedDateTime(LocalDateTime.now());
    }

    private void setCreatedDate(AdEntity adEntity){
        adEntity.setCreatedDateTime(LocalDateTime.now());
    }

    public void highlightAd(Long adId){
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setExpirationHighlightTime(LocalDateTime.now().plusMinutes(1));
        adRepository.save(adEntity);
    }

    public void refreshAd(Long adId){
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setRefreshTime(LocalDateTime.now());
        adRepository.save(adEntity);
    }

 /*   public void changeTitle(String newTitle, Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setTitle(newTitle);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changeAdCategory(AdCategory newAdCategory, Long adId)  {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setAdCategory(newAdCategory);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changeImagePath(String newImagePath, Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setImagePath(newImagePath);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changeDescription(String newDescription, Long adId){
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setDescription(newDescription);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changePrice(Double newPrice, Long adId){
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setPrice(newPrice);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changeCity(String newCity, Long adId){
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setCity(newCity);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void changeActiveStatus(Boolean isActive, Long adId){
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setIsActive(isActive);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }*/
}


