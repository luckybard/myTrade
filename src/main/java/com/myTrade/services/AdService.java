package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.AdCategory;
import com.myTrade.utility.City;
import com.myTrade.utility.PriceRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private AdRepository adRepository;
    private UserRepository userRepository;
    private final AdMapperImpl adMapper = new AdMapperImpl();

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public AdDto fetchAdDtoById(Long adId) {
        return adMapper.adEntityToAdDto(adRepository.getById(adId));
    }

    public void patchAdDto(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setModifiedDate(adEntity);
        adRepository.save(adEntity);
    }

    public void saveAdDtoAndAddAdToUserAdList(AdDto adDto) {
        Long adEntityId = saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(adDto);
        UserEntity adOwner = userRepository.findByUsername(adDto.getOwnerUsername());//TODO: differance between .get() and adding Optional<UserEntity>
        List<AdEntity> adEntityList = adOwner.getAdEntityList().stream().collect(Collectors.toList());
        adEntityList.add(adRepository.getById(adEntityId));
        adOwner.setAdEntityList(adEntityList);
        userRepository.save(adOwner);
    }

    public Long saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        setCreatedDate(adEntity);
        setModifiedDate(adEntity);
        setRefreshDate(adEntity);
        setHighlightExpirationTime(adEntity);
        return adRepository.save(adEntity).getId();
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

    public Page<AdEntity> findAllActiveByAdSearchRequest(String searchText, Boolean searchInDescription, City city,
                                                         AdCategory category, PriceRange priceRange, PageRequest pageRequest) {
        if (searchInDescription) {
            return adRepository.findBySearchRequest(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), pageRequest);
        } else
            return adRepository.findBySearchRequestWithoutDescription(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), pageRequest);
    }

    //TODO: Fronted, maximum search is by 10 words.
    public Page<AdEntity> findAllActiveByAdSearchRequestUpgraded(String searchText, City city, AdCategory
            category, PriceRange priceRange, PageRequest pageRequest) {
        String[] textsToBeSearched = searchText.toLowerCase().split(" ");
        String[] texts = new String[10];
        if (textsToBeSearched.length < 10) {
            for (int i = 0; i < textsToBeSearched.length - 1; i++) {
                texts[i] = "%" + textsToBeSearched[i] + "%";
            }
            for (int i = textsToBeSearched.length - 1; i < 10; i++) {
                texts[i] = "%" + textsToBeSearched[textsToBeSearched.length - 1] + "%";
            }
        }
        return adRepository.findBySearchRequestUpgraded(city.getCityName(), category.getCategory(),
                priceRange.getFrom(), priceRange.getTo(), texts[0], texts[1], texts[2], texts[3], texts[4], texts[5],
                texts[6], texts[7], texts[8], texts[9], pageRequest);
    }
}


