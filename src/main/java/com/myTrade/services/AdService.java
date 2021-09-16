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

    public List<AdDto> fetchByAdSearchRequest(AdSearchRequest request) {
        String category = request.getAdCategory().toString();
        String city = request.getCity().toString();
        if(city.equals("All")){
            city = "%_%";
        }
        int priceFrom = request.getPriceRange().getFrom();
        int priceTo = request.getPriceRange().getTo();
        return adMapper.adEntityListToAdDtoList(search(request,adRepository.findAdEntitiesByAdRequest(category, city, priceFrom, priceTo)));
    }
}


