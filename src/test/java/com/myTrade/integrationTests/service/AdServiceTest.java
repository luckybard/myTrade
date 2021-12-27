package com.myTrade.integrationTests.service;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.exceptions.AdValidationException;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.AdService;
import com.myTrade.utility.AdUtility;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static com.myTrade.utility.TestUtility.*;
import static com.myTrade.utility.UserUtility.POINTS_COST_OF_HIGHLIGHTING_AD;
import static com.myTrade.utility.UserUtility.getUsernameFromContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "brad@brad.brad")
class AdServiceTest {
    private AdService adService;
    private AdRepository adRepository;
    private UserRepository userRepository;

    @Autowired
    public AdServiceTest(AdService adService, AdRepository adRepository, UserRepository userRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void refreshAdById_adId_shouldSetNewRefreshTime(Long adId) {
        //given
        LocalDate expectedRefreshTime = LocalDate.now();
        //when
        adService.refreshAdById(adId);
        LocalDate actualRefreshTime = adRepository.getById(adId).getRefreshDate();
        //then
        assertThat(actualRefreshTime).isEqualTo(expectedRefreshTime);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void changeAdStatusById_adId_shouldChangeAdStatus(Long adId) {
        //given
        Boolean expectedStatus = !adRepository.getById(adId).getIsActive();
        //when
        adService.changeAdStatusById(adId);
        Boolean actualStatus = adRepository.getById(adId).getIsActive();
        //then
        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void highlightAdByIdAndDeductHighlightPointFromUser_adId_shouldHighlightAd(Long adId) {
        //given
        //when
        adService.highlightAdByIdAndDeductHighlightPointFromUser(adId);
        //then
        AdEntity actualAdEntity = adRepository.getById(adId);
        actualAdEntity.postLoad();
        assertThat(actualAdEntity.getIsHighlighted()).isTrue();
    }

    @Test
    public void deductHighlightPointFromUser_shouldDeductHighlightPointsFromUserAccount() {
        //given
        Integer userHighlightPoints = userRepository.getByUsername(getUsernameFromContext()).getHighlightPoints();
        Integer expectedHighlightPoints = userHighlightPoints - POINTS_COST_OF_HIGHLIGHTING_AD;
        //when
        adService.deductHighlightPointFromUser();
        Integer actualUserHighlightPoints = userRepository.getByUsername(getUsernameFromContext()).getHighlightPoints();
        //then
        assertThat(actualUserHighlightPoints).isEqualTo(expectedHighlightPoints);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 8})
    public void fetchRandomAdDtoPage_pageSize_shouldRetrieveRandomAdsDtoPage(int pageSize) {
        //given
        //when
        Page<AdDto> adDtoPage = adService.fetchRandomAdDtoPage(pageSize);
        //then
        assertThat(adDtoPage.getContent()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"brad@brad.brad", "john@john.john"})
    public void fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite_usernameAndPageRequest_shouldRetrieveOwnerAdsDtoPage(String username) {
        //given
        //when
        Page<AdDto> adDtoPage = adService.fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(username, DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        assertThat(adDtoPage.getContent()).isNotNull();
        assertThat(adDtoPage.get().findFirst().get().getOwnerUsername()).isEqualTo(username);

    }


    @Test
    public void fetchUserFavouriteAdDtoPage_pageRequest_shouldRetrieveUserFavouriteAdDtoPage() {
        //given
        Long expectedAmountOfUserFavouriteAds = Long.valueOf(userRepository.getByUsername(getUsernameFromContext()).getFavouriteAdEntityList().size());
        //when
        Page<AdDto> adDtoPage = adService.fetchUserFavouriteAdDtoPage(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        assertThat(adDtoPage.getContent()).isNotNull();
        assertThat(adDtoPage.getTotalElements()).isEqualTo(expectedAmountOfUserFavouriteAds);
    }

    @Test
    public void fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh_pageRequest_shouldRetrieveAdOwnerDtoPage(){
        //given
        Long expectedAmountOfUserAds = Long.valueOf(userRepository.getByUsername(getUsernameFromContext()).getAdEntityList().size());
        //when
        Page<AdOwnerDto> adOwnerDtoPage = adService.fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        assertThat(adOwnerDtoPage.getContent()).isNotNull();
        assertThat(adOwnerDtoPage.getTotalElements()).isEqualTo(expectedAmountOfUserAds);

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void patchAdEntityByAdEditDto_adId_thenAdShouldBeUpdated(Long adId) {
        AdEditDto adEditDto = AdEditDto.builder().id(adId)
                .adCategory(AdCategory.OTHER)
                .city(City.EVERYWHERE)
                .description("description,description,description,description,description,description")
                .title("title,title")
                .price(1D)
                .build();
        //when
        adService.patchAdEntityByAdEditDto(adEditDto);
        AdEntity actualAdEntity = adRepository.getById(adId);
        //then
        assertThat(actualAdEntity).isNotNull();
        assertThat(actualAdEntity.getTitle()).isEqualTo(adEditDto.getTitle());
        assertThat(actualAdEntity.getDescription()).isEqualTo(adEditDto.getDescription());
        assertThat(actualAdEntity.getAdCategory()).isEqualTo(adEditDto.getAdCategory());
        assertThat(actualAdEntity.getCity()).isEqualTo(adEditDto.getCity());
        assertThat(actualAdEntity.getPrice()).isEqualTo(adEditDto.getPrice());

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEditDtoWithoutId.csv", numLinesToSkip = 1)
    public void saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList_adEditDtoWithoutId_shouldSaveAdAndAssignToUserAdList(AdCategory adCategory,
                                                                                                                             String title,
                                                                                                                             String description,
                                                                                                                             City city,
                                                                                                                             Double price) {
        //given
        AdEditDto adEditDto = AdEditDto.builder()
                .adCategory(adCategory)
                .city(city)
                .description(description)
                .title(title)
                .price(price)
                .build();
        int expectedUserAdListSize = userRepository.getByUsername(getUsernameFromContext()).getAdEntityList().size() + ONE_ADDITIONAL_AD;
        //when
        adService.saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(adEditDto);
        //then
        int actualUserAdListSize = userRepository.getByUsername(getUsernameFromContext()).getAdEntityList().size();
        assertThat(actualUserAdListSize).isEqualTo(expectedUserAdListSize);
    }

    @ParameterizedTest
    @MethodSource("invalidAdEditDto")
    public void saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList_invalidAdEditDto_shouldThrowAdValidationException(AdEditDto adEditDto) {
        //given
        //when & then
        assertThrows(AdValidationException.class, () -> {
            adService.saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(adEditDto);
        });
    }

    private static Stream<Arguments> invalidAdEditDto() {
        return Stream.of(
                Arguments.of(AdEditDto.builder()
                        .adCategory(AdCategory.OTHER)
                        .title("  ")
                        .description("      ")
                        .city(City.PARIS)
                        .price(0D)
                        .build()),
                Arguments.of(AdEditDto.builder()
                        .adCategory(AdCategory.OTHER)
                        .title("Junior Java Developer")
                        .description("")
                        .city(City.PARIS)
                        .price(2000D)
                        .build())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void fetchAdEditDto_adId_shouldRetrieveAdEditDto(Long adId) {
        //given
        //when
        AdEditDto adEditDto = adService.fetchAdEditDto(adId);
        //then
        assertThat(adEditDto).isNotNull();
        assertThat(adEditDto.getTitle()).isNotEmpty();
        assertThat(adEditDto.getDescription()).isNotEmpty();
    }

    @Test
    public void getUserFavouriteAdsId_shouldRetrieveUserFavouriteAdsIdList() {
        //given
        //when
        List<Long> userFavouriteAdsIdList = adService.getUserFavouriteAdsId();
        //then
        assertThat(userFavouriteAdsIdList.size()).isGreaterThan(0);
    }

    @Test
    public void setIsAdUserFavourite_adEntity_shouldSetIsAdUserFavourite() {
        //given
        Long userFavouriteAdId = userRepository.getByUsername(getUsernameFromContext()).getFavouriteAdEntityList().stream()
                .findFirst()
                .get()
                .getId();
        AdEntity userFavouriteAdEntity = adRepository.getById(userFavouriteAdId);
        //when
        adService.setIsAdUserFavourite(userFavouriteAdEntity);
        //then
        assertThat(userFavouriteAdEntity.getIsUserFavourite()).isTrue();
    }

    @Test
    public void setIsAdUserFavourite_adEntityPage_shouldSetIsAdsUserFavourite() {
        //given
        Page<AdEntity> userFavouriteAdEntityPage = adRepository.findUserFavouriteAdEntityPageByUserId(
                userRepository.getByUsername(getUsernameFromContext()).getId(), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        //when
        adService.setIsAdUserFavourite(userFavouriteAdEntityPage);
        //then
        assertThat(userFavouriteAdEntityPage.get().findFirst().get().getIsUserFavourite()).isTrue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adId.csv", numLinesToSkip = 1)
    public void addAdViewCounter_adId_shouldAddAdViewCount(Long adId) {
        //given
        AdEntity adEntity = adRepository.getById(adId);
        Long expectedAdViewsAmount = adEntity.getCountView() + AdUtility.AD_VIEW_COUNT;
        //when
        adService.addAdViewCounter(adEntity);
        //then
        Long actualAdViewsAmount = adRepository.getById(adId).getCountView();
        assertThat(actualAdViewsAmount).isEqualTo(expectedAdViewsAmount);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adId.csv", numLinesToSkip = 1)
    public void fetchAdDtoByIdAndSetIsUserFavourite_adId_shouldRetrieveAdDto(Long adId) {
        //given
        //when
        AdDto adDto = adService.fetchAdDtoByIdAndSetIsUserFavourite(adId);
        //then
        assertThat(adDto).isNotNull();
        assertThat(adDto.getTitle()).isNotNull();
        assertThat(adDto.getDescription()).isNotNull();
    }
}

