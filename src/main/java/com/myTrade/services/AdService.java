package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.utility.searchEngine.AdSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.myTrade.utility.searchEngine.SearchEngine.search;

@Service
public class AdService {

    private AdRepository adRepository;
    private final AdMapperImpl adMapper = new AdMapperImpl();

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public AdDto fetchAdDtoById(Long adId) {
        return adMapper.adEntityToAdDto(adRepository.getById(adId));
    }

    public void patchAdDto(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setCreatedDate(adEntity);
        setModifiedDate(adEntity);
        setRefreshDate(adEntity);
        setHighlightExpirationTime(adEntity);
        adRepository.save(adEntity);
    }

    private void setModifiedDate(AdEntity adEntity) {
        adEntity.setModifiedDateTime(LocalDateTime.now());
    }

    private void setCreatedDate(AdEntity adEntity) {
        adEntity.setCreatedDateTime(LocalDateTime.now());
    }

    private void setRefreshDate(AdEntity adEntity) {
        adEntity.setRefreshTime(LocalDateTime.now());
    }

    private void setHighlightExpirationTime(AdEntity adEntity) {
        adEntity.setExpirationHighlightTime(LocalDateTime.of(LocalDate.of(1990, 1, 1), LocalTime.of(1, 0)));
    }

    public void highlightAd(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setExpirationHighlightTime(LocalDateTime.now().plusMinutes(5));
        adRepository.save(adEntity);
    }

    public void refreshAd(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setRefreshTime(LocalDateTime.now());
        adRepository.save(adEntity);
    }

    public List<AdDto> fetchByAllAdSearchRequest(AdSearchRequest adSearchRequest) {
        return adMapper.adEntityListToAdDtoList(search(adSearchRequest, adRepository.findAdEntitiesByIsActiveTrue()));
    }

//    public List<AdDto> fetchAllAdsWhichContainsProvidedWords(String word) {
//        String [] providedWords = word.toLowerCase(Locale.ROOT).split(" ");
//        List<AdEntity> adEntityList = adRepository.findAll();
//        List<AdEntity> result = new LinkedList<>();
//        for(String key:providedWords) {
//            result.addAll(adEntityList.stream().filter(ad -> ad.getTitle().toLowerCase(Locale.ROOT).contains(key) || ad.getDescription().toLowerCase(Locale.ROOT).contains(key)).collect(Collectors.toList()));
//        }
//        result = result.stream().distinct().collect(Collectors.toList());
//        return adMapper.adEntityListToAdDtoList(result);
//    }
//
//    public List<AdDto> fetchAllAdsWhichContainsProvidedWordsVersionFromDB(String word){
//        String [] providedWords = word.toLowerCase(Locale.ROOT).split(" ");
//        List<AdEntity> result = new LinkedList<>();
//        for(String key:providedWords) {
//           result.addAll(adRepository.findAdEntitiesByProvidedValue("%"+key.toLowerCase(Locale.ROOT)+"%"));
//        }
//        return adMapper.adEntityListToAdDtoList(result.stream().distinct().collect(Collectors.toList()));
//    }



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


