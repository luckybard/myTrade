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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserService userService;
    private final AdMapperImpl adMapper = new AdMapperImpl();

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository, UserService userService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public AdDto fetchAdDtoById(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        addAdView(adEntity);
        setIsUserFavouriteAd(adEntity);
        return adMapper.adEntityToAdDto(adEntity);
    }

    public AdDto fetchAdForEdit(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        UserEntity userEntity = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (adEntity.getOwnerUsername().equals(userEntity.getUsername())) {
           checkIsHighlightable(adEntity,userEntity);
            return adMapper.adEntityToAdDto(adEntity);
        } else return null; //TODO: [Q] How about to add exception here about unauthorized user? Or response status 401?
    }

    private void checkIsHighlightable(AdEntity adEntity, UserEntity userEntity) {
        if(!adEntity.getIsHighlighted() && userEntity.getHighlightPoint() > 0){
            adEntity.setIsHighlightable(true);
        }
    }

    public void patchAdDto(AdDto adDto) { //TODO: [Q] How to properly updateEntity
        AdEntity toBeSaved = adRepository.findById(adDto.getId()).get();
        toBeSaved.setTitle(adDto.getTitle());
        toBeSaved.setDescription(adDto.getDescription());
        toBeSaved.setPrice(adDto.getPrice());
        toBeSaved.setAdCategory(adDto.getAdCategory());
        setModifiedDate(toBeSaved);
        adRepository.save(toBeSaved);
    }

    //TODO: Change process of creating ad
    public void saveAdDtoAndAddAdToUserAdList(AdDto adDto) {
        Long adEntityId = saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(adDto);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity adOwner = userRepository.findByUsername(username);//TODO: [Q] Differance between .get() and adding Optional<UserEntity>
        List<AdEntity> adEntityList = adOwner.getAdEntityList().stream().collect(Collectors.toList());
        adEntityList.add(adRepository.getById(adEntityId));
        adOwner.setAdEntityList(adEntityList);
        userRepository.save(adOwner);
    }

    public Long saveAdDtoWithProperValuesOfCreatedModifiedRefreshHighlightDateTime(AdDto adDto) {
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto); //TODO:[Q] Better to save AdDto via upper method with (cascade = {CascadeType.ALL})
        setCreatedDate(adEntity);
        setModifiedDate(adEntity);
        setRefreshDate(adEntity);
        setHighlightExpirationTime(adEntity);
        adEntity.setIsActive(true);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        adEntity.setOwnerUsername(username);

        return adRepository.save(adEntity).getId();
    }

    private void setModifiedDate(AdEntity adEntity) { //TODO:[Q] Should these methods be extract to other classes as utility?
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
        adEntity.setExpirationHighlightTime(LocalDateTime.now().plusDays(1));
        adRepository.save(adEntity);
        userService.deductHighlightPoint();    //TODO:[Q]Is it possible to make transaction like in hibernate, to have confidence that whole code has been completed, try-catch? @Transactional
    }

    public void refreshAd(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setRefreshTime(LocalDateTime.now());
        adRepository.save(adEntity);
    }

    public void addAdView(AdEntity adEntity) {
        adEntity.setCountView(adEntity.getCountView() + 1);
        adRepository.save(adEntity);
    }

    //TODO: LastViewed (Lately Fetched Ads, limit to 20), form of queue?
//    public void addAdEntityToLastViewedQueue(Long adId,String username){
//        UserEntity userEntity = userRepository.findByUsername(username);
//        Queue<AdEntity> adEntityQueue = userRepository.findByUsername(username).getLastViewedAdEntityQueueList();
//        if(adEntityQueue.size() > 10){
//            adEntityQueue.remove();
//        }
//        adEntityQueue.add(adRepository.findById(adId).get());
//        userEntity.setLastViewedAdEntityQueueList(adEntityQueue);
//        userRepository.save(userEntity);
//    }


    public Page<AdDto> findAllActiveByAdSearchRequest(String searchText, Boolean searchInDescription, City city,
                                                      AdCategory category, PriceRange priceRange, Integer pageNumber, Integer pageSize) {
        Page<AdEntity> adEntityPage = null;
        if (searchInDescription) {
            adEntityPage = adRepository.findBySearchRequest(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), PageRequest.of(pageNumber, pageSize, Sort.by("refresh_time").descending()));
        } else {
            adEntityPage = adRepository.findBySearchRequestWithoutDescription(category.getCategory(), city.getCityName(), priceRange.getFrom(),
                    priceRange.getTo(), searchText.toLowerCase(), PageRequest.of(pageNumber, pageSize, Sort.by("refresh_time").descending()));
        }
        setIsUserFavouriteAds(adEntityPage);
        return adEntityPage.map(adEntity -> adMapper.adEntityToAdDto(adEntity));
    }

//    //TODO: Fronted, maximum search is by 10 words.
//    public Page<AdDto> findAllActiveByAdSearchRequestUpgraded(String searchText, City city, AdCategory
//            category, PriceRange priceRange, PageRequest pageRequest) {
//        String[] textsToBeSearched = searchText.toLowerCase().split(" ");
//        String[] texts = new String[10];
//        if (textsToBeSearched.length < 10) {
//            for (int i = 0; i < textsToBeSearched.length - 1; i++) {
//                texts[i] = "%" + textsToBeSearched[i] + "%";
//            }
//            for (int i = textsToBeSearched.length - 1; i < 10; i++) {
//                texts[i] = "%" + textsToBeSearched[textsToBeSearched.length - 1] + "%";
//            }
//        }
//        Page<AdEntity> adEntityPage = adRepository.findBySearchRequestUpgraded(city.getCityName(), category.getCategory(),
//                priceRange.getFrom(), priceRange.getTo(), texts[0], texts[1], texts[2], texts[3], texts[4], texts[5],
//                texts[6], texts[7], texts[8], texts[9], pageRequest);
//        return adEntityPage.map(adEntity -> adMapper.adEntityToAdDto(adEntity));
//    }

    public Page<AdEntity> fetchRandom(Integer pageSize) {
        return adRepository.findAll(PageRequest.of(0, pageSize));
    }

    public AdEntity setIsUserFavouriteAd(AdEntity adEntity) { //TODO: [Q] Extract predicate?
        String username = "anonymousUser"; //TODO: [Q] Replace for static instance from prop?
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase(username)) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Long> userFavouriteAdsByAdId = userRepository.findByUsername(username).getFavouriteAdEntityList()
                    .stream().map(ad -> ad.getId()).collect(Collectors.toList());
            if (userFavouriteAdsByAdId.contains(adEntity.getId().longValue())) {
                adEntity.setIsUserFavourite(true);
            }
        }
        return adEntity;
    }

    public Page<AdEntity> setIsUserFavouriteAds(Page<AdEntity> adEntityPage) {
        String username = "anonymousUser";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase(username)) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Long> userFavouriteAdsByAdId = userRepository.findByUsername(username).getFavouriteAdEntityList()
                    .stream().map(adEntity -> adEntity.getId()).collect(Collectors.toList());
            adEntityPage.stream().filter(adEntity -> userFavouriteAdsByAdId.contains(adEntity.getId().longValue())).forEach(adEntity -> adEntity.setIsUserFavourite(true));
        }
        return adEntityPage;
    }

    public void changeActiveStatus(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        if (adEntity.getIsActive()) {
            adEntity.setIsActive(false);
        } else {
            adEntity.setIsActive(true);
        }
        adRepository.save(adEntity);
    }
}


