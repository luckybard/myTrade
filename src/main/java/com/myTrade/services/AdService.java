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
import com.myTrade.utility.pojo.SearchRequest;
import com.myTrade.validators.adForm.AdFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.myTrade.utility.AdUtility.*;
import static com.myTrade.utility.UserUtility.*;
import static com.myTrade.utility.pojo.SortType.CREATED_DATE;
import static com.myTrade.utility.pojo.SortType.REFRESH;

@Service
public final class AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapperImpl adMapper = new AdMapperImpl();
    private final AdFormValidator adFormValidator = new AdFormValidator();

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public Page<AdDto> fetchActiveAdDtoPageBySearchRequest(SearchRequest sr) {
        Page<AdEntity> adEntityPage = sr.getIsSearchedInDescription() ?
                findActiveAdDtoPageBySearchRequest(sr)
                : findActiveAdDtoPageBySearchRequestWithoutDescription(sr);
        setIsAdUserFavourite(adEntityPage);
        return adEntityPage.map(adMapper::adEntityToAdDto);
    }

    private Page<AdEntity> findActiveAdDtoPageBySearchRequest(SearchRequest sr) {
        return adRepository.findActiveAdEntityPageBySearchRequest(sr.getAdCategory().getCategory(),
                sr.getCity().getCityName(), sr.getPriceFrom(), sr.getPriceTo(), sr.getSearchText().toLowerCase(Locale.ROOT),
                PageRequest.of(sr.getPageNumber(), sr.getPageSize(), Sort.by(REFRESH.getValue()).descending()));
    }

    private Page<AdEntity> findActiveAdDtoPageBySearchRequestWithoutDescription(SearchRequest sr) {
        return adRepository.findActiveAdEntityPageBySearchRequestWithoutDescription(sr.getAdCategory().getCategory(),
                sr.getCity().getCityName(), sr.getPriceFrom(), sr.getPriceTo(), sr.getSearchText().toLowerCase(Locale.ROOT),
                PageRequest.of(sr.getPageNumber(), sr.getPageSize(), Sort.by(REFRESH.getValue()).descending()));
    }

    public AdDto fetchAdDtoByIdAndSetIsUserFavourite(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        addAdViewCounter(adEntity);
        setIsAdUserFavourite(adEntity);
        return adMapper.adEntityToAdDto(adEntity);
    }

    public void addAdViewCounter(AdEntity adEntity) {
        adEntity.setCountView(adEntity.getCountView() + AD_VIEW_COUNT);
        adRepository.save(adEntity);
    }

    public void setIsAdUserFavourite(Object object) {
        if (!isUserAnonymous()) {
            List<Long> userFavouriteAdsIdList = getUserFavouriteAdsId();
            if (object instanceof AdEntity && userFavouriteAdsIdList.contains(((AdEntity) object).getId())) {
                ((AdEntity) object).setIsUserFavourite(true);
            } else if (object instanceof Page) {
                ((Page<AdEntity>) object).stream()
                        .filter(adEntity -> userFavouriteAdsIdList.contains(adEntity.getId()))
                        .forEach(adEntity -> adEntity.setIsUserFavourite(true));
            }
        }
    }

    public List<Long> getUserFavouriteAdsId() {
        return userRepository.getByUsername(getUsernameFromContext())
                .getFavouriteAdEntityList()
                .stream()
                .map(AdEntity::getId)
                .collect(Collectors.toList());
    }

    public AdEditDto fetchAdEditDto(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        UserEntity userEntity = userRepository.getByUsername(getUsernameFromContext());
        if (isUserAdOwner(adEntity, userEntity)) {
            return adMapper.adEntityToAdEditDto(adEntity);
        } else {
            throw new UserAuthorizationException();
        }
    }

    public void saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(AdEditDto adEditDto) {
        if (adFormValidator.test(adEditDto)) {
            UserEntity adOwner = userRepository.getByUsername(getUsernameFromContext());
            AdEntity adEntity = adMapper.adEditDtoToAdEntity(adEditDto);
            setInitialValuesForAd(adEntity, adOwner.getUsername());
            saveAdAndAssignToUserAdList(adOwner, adEntity);
        } else throw new AdValidationException();
    }

    public void saveAdAndAssignToUserAdList(UserEntity adOwner, AdEntity adEntity) {
        List<AdEntity> ownerAdEntityList = new ArrayList<>(adOwner.getAdEntityList());
        ownerAdEntityList.add(adEntity);
        adOwner.setAdEntityList(ownerAdEntityList);
        userRepository.save(adOwner);
    }

    public void patchAdEntityByAdEditDto(AdEditDto adEditDto) {
        if (adFormValidator.test(adEditDto)) {
            AdEntity adEntity = adRepository.getById(adEditDto.getId());
            setValuesFromAdEditDto(adEntity, adEditDto);
            adRepository.save(adEntity);
        } else {
            throw new AdValidationException();
        }
    }

    public Page<AdOwnerDto> fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(Integer pageNumber, Integer pageSize) {
        UserEntity user = userRepository.getByUsername(getUsernameFromContext());
        Page<AdEntity> adEntityPage = adRepository.findAdEntityPageByOwnerUsername(user.getUsername(),
                PageRequest.of(pageNumber, pageSize, Sort.by(CREATED_DATE.getValue()).descending()));
        for (AdEntity adEntity : adEntityPage) {
            checkIsUserAbleToHighlight(adEntity, user);
            checkIsAdRefreshable(adEntity);
        }
        return adEntityPage.map(adMapper::adEntityToAdOwnerDto);
    }

    public Page<AdDto> fetchUserFavouriteAdDtoPage(Integer pageNumber, Integer pageSize) {
        UserEntity userEntity = userRepository.getByUsername(getUsernameFromContext());
        Page<AdEntity> adEntityPage = adRepository.findUserFavouriteAdEntityPageByUserId(userEntity.getId(), PageRequest.of(pageNumber, pageSize));
        setIsAdUserFavourite(adEntityPage);
        return adEntityPage.map(adMapper::adEntityToAdDto);
    }

    public Page<AdDto> fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(String username, Integer pageNumber, Integer pageSize) {
        Page<AdEntity> adEntityPage = adRepository.findActiveAdEntityPageByOwnerUsername(username, PageRequest.of(pageNumber, pageSize, Sort.by(CREATED_DATE.getValue()).descending()));
        setIsAdUserFavourite(adEntityPage);
        return adEntityPage.map(adMapper::adEntityToAdDto);
    }

    public Page<AdDto> fetchRandomAdDtoPage(Integer pageSize) {
        Page<AdEntity> adEntityPage = adRepository.findActiveRandomAdEntityPage(PageRequest.of(0, pageSize));
        setIsAdUserFavourite(adEntityPage);
        return adEntityPage.map(adMapper::adEntityToAdDto);
    }

    public void highlightAdByIdAndDeductHighlightPointFromUser(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setExpirationHighlightDate(LocalDate.now().plusDays(AD_HIGHLIGHTING_DURATION_IN_DAYS));
        adRepository.save(adEntity);
        deductHighlightPointFromUser();
    }

    public void deductHighlightPointFromUser() {
        UserEntity userEntity = userRepository.getByUsername(getUsernameFromContext());
        if (checkIfUserHaveEnoughHighlightPoints(userEntity)) {
            userEntity.setHighlightPoints(userEntity.getHighlightPoints() - POINTS_COST_OF_HIGHLIGHTING_AD);
            userRepository.save(userEntity);
        }
    }

    public void changeAdStatusById(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setIsActive(!adEntity.getIsActive());
        adRepository.save(adEntity);
    }

    public void refreshAdById(Long adId) {
        AdEntity adEntity = adRepository.getById(adId);
        adEntity.setRefreshDate(LocalDate.now());
        adRepository.save(adEntity);
    }
}


