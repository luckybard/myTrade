package com.myTrade.services;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.exceptions.AdValidationException;
import com.myTrade.exceptions.UserAuthorizationException;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import com.myTrade.utility.pojo.PriceRange;
import com.myTrade.validators.adForm.AdFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.myTrade.utility.AdUtility.*;
import static com.myTrade.utility.UserUtility.*;
import static com.myTrade.utility.pojo.SortType.CREATED_DATE;
import static com.myTrade.utility.pojo.SortType.REFRESH;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapperImpl adMapper = new AdMapperImpl();
    private final AdFormValidator adFormValidator = new AdFormValidator();

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Page<AdDto>> fetchActiveAdDtoPageBySearchRequest(String searchText, Boolean searchInDescription,
                                                                           City city, AdCategory category, PriceRange priceRange,
                                                                           Integer pageNumber, Integer pageSize) {
        Page<AdEntity> adEntityPage;
        if (searchInDescription) {
            adEntityPage = findActiveAdDtoPageBySearchRequest(searchText, city, category, priceRange, pageNumber, pageSize);
        } else {
            adEntityPage = findActiveAdDtoPageBySearchRequestWithoutDescription(searchText, city, category, priceRange, pageNumber, pageSize);
        }
        if (!isUserAnonymous()) {
            setIsAdUserFavourite(adEntityPage);
        }
        Page<AdDto> adDtoPage = adEntityPage.map(adMapper::adEntityToAdDto);
        return ResponseEntity.ok(adDtoPage);
    }

    private Page<AdEntity> findActiveAdDtoPageBySearchRequest(String searchText, City city, AdCategory category, PriceRange priceRange, Integer pageNumber, Integer pageSize) {
        return adRepository.findActiveAdEntityPageBySearchRequest(category.getCategory(),
                city.getCityName(), priceRange.getFrom(), priceRange.getTo(), searchText.toLowerCase(),
                PageRequest.of(pageNumber, pageSize, Sort.by(REFRESH.getValue()).descending()));
    }

    private Page<AdEntity> findActiveAdDtoPageBySearchRequestWithoutDescription(String searchText, City city, AdCategory category, PriceRange priceRange, Integer pageNumber, Integer pageSize) {
        return adRepository.findActiveAdEntityPageBySearchRequestWithoutDescription(category.getCategory(),
                city.getCityName(), priceRange.getFrom(), priceRange.getTo(), searchText.toLowerCase(),
                PageRequest.of(pageNumber, pageSize, Sort.by(REFRESH.getValue()).descending()));
    }

    public ResponseEntity<AdDto> fetchAdDtoByIdAndSetIsUserFavourite(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        addAdViewCounter(adEntity);
        if (!isUserAnonymous()) {
            setIsAdUserFavourite(adEntity);
        }
        AdDto adDto = adMapper.adEntityToAdDto(adEntity);
        return ResponseEntity.ok(adDto);
    }

    public void addAdViewCounter(AdEntity adEntity) {
        adEntity.setCountView(adEntity.getCountView() + AD_VIEW_COUNT);
        adRepository.save(adEntity);
    }

    public Object setIsAdUserFavourite(Object object) {
        List<Long> userFavouriteAdsIdList = getUserFavouriteAdsId();
        if (object instanceof AdEntity && userFavouriteAdsIdList.contains(((AdEntity) object).getId())) {
            ((AdEntity) object).setIsUserFavourite(true);
        } else {
            ((Page<AdEntity>) object).stream()
                    .filter(adEntity -> userFavouriteAdsIdList.contains(adEntity.getId()))
                    .forEach(adEntity -> adEntity.setIsUserFavourite(true));
        }
        return object;
    }

    private List<Long> getUserFavouriteAdsId() {
        return userRepository.findByUsername(getUsernameFromContext())
                .getFavouriteAdEntityList()
                .stream()
                .map(AdEntity::getId)
                .collect(Collectors.toList());
    }

    public ResponseEntity<AdEditDto> fetchAdEditDto(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        UserEntity userEntity = userRepository.findByUsername(getUsernameFromContext());
        if (isUserAdOwner(adEntity, userEntity)) {
            AdEditDto adEditDto = adMapper.adEntityToAdEditDto(adEntity);
            return ResponseEntity.ok(adEditDto);
        } else {
            throw new UserAuthorizationException();
        }
    }

    public ResponseEntity saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(AdEditDto adEditDto) {
        if (adFormValidator.test(adEditDto)) {
            UserEntity adOwner = userRepository.findByUsername(getUsernameFromContext());
            AdEntity adEntity = adMapper.adEditDtoToAdEntity(adEditDto);
            setInitialValuesForAd(adEntity, adOwner.getUsername());
            saveAdAndAssignToUserAdList(adOwner, adEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        throw new AdValidationException();
    }

    private void saveAdAndAssignToUserAdList(UserEntity adOwner, AdEntity adEntity) {
        List<AdEntity> ownerAdEntityList = new ArrayList<>(adOwner.getAdEntityList());
        ownerAdEntityList.add(adEntity);
        adOwner.setAdEntityList(ownerAdEntityList);
        userRepository.save(adOwner);
    }

    public ResponseEntity patchAdEntityByAdEditDto(AdEditDto adEditDto) {
        if (adFormValidator.test(adEditDto)) {
            AdEntity adEntity = adRepository.findById(adEditDto.getId()).get();
            setValuesFromAdFormDto(adEntity, adEditDto);
            adRepository.save(adEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new AdValidationException();
        }
    }

    public ResponseEntity<Page<AdOwnerDto>> fetchUserAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(Integer pageNumber, Integer pageSize) {
        UserEntity user = userRepository.findByUsername(getUsernameFromContext());
        Page<AdEntity> adEntityPage = adRepository.findAdEntityPageByOwnerUsername(user.getUsername(),
                PageRequest.of(pageNumber, pageSize, Sort.by(CREATED_DATE.getValue()).descending()));
        for (AdEntity adEntity : adEntityPage) {
            checkIsUserAbleToHighlight(adEntity, user);
            checkIsAdRefreshable(adEntity);
        }
        Page<AdOwnerDto> adUserDtoPage = adEntityPage.map(adMapper::adEntityToAdOwnerDto);
        return ResponseEntity.ok(adUserDtoPage);
    }

    public ResponseEntity<Page<AdDto>> fetchUserFavouriteAdDtoPage(Integer pageNumber, Integer pageSize) {
        UserEntity userEntity = userRepository.findByUsername(getUsernameFromContext());
        Page<AdEntity> adEntityPage = adRepository.findUserFavouriteAdEntityPageByUserId(userEntity.getId(), PageRequest.of(pageNumber, pageSize));
        setIsAdUserFavourite(adEntityPage);
        Page<AdDto> adDtoPage = adEntityPage.map(adMapper::adEntityToAdDto);
        return ResponseEntity.ok(adDtoPage);
    }

    public ResponseEntity<Page<AdDto>> fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(String username, Integer pageNumber, Integer pageSize) {
        Page<AdEntity> adEntityPage = adRepository.findAdEntityPageByOwnerUsername(username, PageRequest.of(pageNumber, pageSize, Sort.by(CREATED_DATE.getValue()).descending()));
        setIsAdUserFavourite(adEntityPage);
        Page<AdDto> adDtoPage = adEntityPage.map(adMapper::adEntityToAdDto);
        return ResponseEntity.ok(adDtoPage);
    }

    public ResponseEntity<Page<AdDto>> fetchRandomAdDtoPage(Integer pageSize) {
        Page<AdDto> pageAdDto = adRepository.findAll(PageRequest.of(0, pageSize)).map(adMapper::adEntityToAdDto);
        return ResponseEntity.ok(pageAdDto);
    }

    public ResponseEntity highlightAdByIdAndDeductHighlightPointFromUser(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setExpirationHighlightDate(LocalDate.now().plusDays(ONE_DAY));
        adRepository.save(adEntity);
        deductHighlightPointFromUser();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public void deductHighlightPointFromUser() {
        UserEntity userEntity = userRepository.findByUsername(getUsernameFromContext());
        if (checkIfUserHaveEnoughHighlightPoints(userEntity)) {
            userEntity.setHighlightPoints(userEntity.getHighlightPoints() - POINTS_COST_OF_HIGHLIGHTING_AD);
        }
    }

    public ResponseEntity changeAdStatusById(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setIsActive(!adEntity.getIsActive());
        adRepository.save(adEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity refreshAdById(Long adId) {
        AdEntity adEntity = adRepository.findById(adId).get();
        adEntity.setRefreshDate(LocalDate.now());
        adRepository.save(adEntity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


