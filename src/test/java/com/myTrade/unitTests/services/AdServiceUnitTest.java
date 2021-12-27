package com.myTrade.unitTests.services;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.AdService;
import com.myTrade.utility.UserUtility;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static com.myTrade.utility.AdUtility.AD_HIGHLIGHTING_DURATION_IN_DAYS;
import static com.myTrade.utility.TestUtility.*;
import static com.myTrade.utility.pojo.SortType.CREATED_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdServiceUnitTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdService adService;

    @Captor
    private ArgumentCaptor<AdEntity> adEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    private final UserEntity user = new UserEntity();

    @BeforeEach
    public void beforeEach() {
        setUpDefaultUserEntity(user);
        setUpSecurityContext(user);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv", numLinesToSkip = 1)
    public void fetchAdDtoByIdAndSetIsUserFavourite_adId_shouldRetrieveAdDto(Long id,
                                                                             AdCategory adCategory,
                                                                             String title,
                                                                             String description,
                                                                             City city,
                                                                             Double price,
                                                                             String ownerUsername,
                                                                             Long countView,
                                                                             Boolean isActive,
                                                                             LocalDate createdDate,
                                                                             LocalDate modifiedDate,
                                                                             LocalDate refreshDate,
                                                                             LocalDate expirationHighlightDate) {
        //given
        AdEntity adEntity = AdEntity.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .countView(countView)
                .createdDate(createdDate)
                .description(description)
                .expirationHighlightDate(expirationHighlightDate)
                .isActive(isActive)
                .modifiedDate(modifiedDate)
                .ownerUsername(ownerUsername)
                .price(price)
                .refreshDate(refreshDate)
                .title(title)
                .build();
        adEntity.postLoad();
        given(adRepository.getById(id)).willReturn(adEntity);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        AdDto fetchedAdDto = adService.fetchAdDtoByIdAndSetIsUserFavourite(id);
        //then
        assertThat(fetchedAdDto).hasNoNullFieldsOrProperties();
        verify(adRepository).getById(id);
        verify(adRepository).save(adEntity);
    }

    @Test
    public void setIsAdUserFavourite_adEntityPage_shouldSetIsAdsUserFavourite() {
        //given
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        Page<AdEntity> adEntityPage = new PageImpl<>(user.getFavouriteAdEntityList());
        adEntityPage.forEach(AdEntity::postLoad);
        //when
        adService.setIsAdUserFavourite(adEntityPage);
        //then
        verify(userRepository).getByUsername(user.getUsername());
        assertThat(adEntityPage.stream().filter(AdEntity::getIsUserFavourite).findFirst().get().getIsUserFavourite()).isTrue();
    }

    @Test
    public void setIsAdUserFavourite_adEntity_shouldSetIsAdUserFavourite() {
        //given
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        AdEntity adEntity = user.getFavouriteAdEntityList().stream().findFirst().get();
        adEntity.postLoad();
        //when
        adService.setIsAdUserFavourite(adEntity);
        //then
        verify(userRepository).getByUsername(user.getUsername());
        assertThat(adEntity.getIsUserFavourite()).isTrue();
    }


    @Test
    public void getUserFavouriteAdsId_shouldRetrieveUserFavouriteAdsIdList() {
        //given
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        List<Long> userFavouriteAdsId = adService.getUserFavouriteAdsId();
        //then
        assertThat(userFavouriteAdsId).isNotNull();
        assertThat(userFavouriteAdsId.size()).isEqualTo(user.getFavouriteAdEntityList().size());
        verify(userRepository).getByUsername(user.getUsername());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEditDto.csv", numLinesToSkip = 1)
    public void saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList_adEditDtoWithoutId_shouldSaveAdAndAssignToUserAdList(
            Long id,
            AdCategory adCategory,
            String title,
            String description,
            City city,
            Double price
    ) {
        //given
        AdEditDto adEditDto = AdEditDto.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .description(description)
                .title(title)
                .price(price)
                .build();
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        int expectedAdEntityListSize = user.getAdEntityList().size() + ONE_ADDITIONAL_AD;
        //when
        adService.saveAdByAdEditDtoWithInitialValuesAndAssignToUserAdList(adEditDto);
        //then
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        int actualAdEntityListSize = userEntityArgumentCaptor.getValue().getAdEntityList().size();
        assertThat(actualAdEntityListSize).isEqualTo(expectedAdEntityListSize);
        AdEntity savedAdEntity = userEntityArgumentCaptor.getValue().getAdEntityList().stream().reduce((first, second) -> second).get();
        assertThat(savedAdEntity.getOwnerUsername()).isNotNull();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEditDto.csv", numLinesToSkip = 1)
    public void patchAdEntityByAdEditDto_adId_thenAdShouldBeUpdated(Long id,
                                                                    AdCategory adCategory,
                                                                    String title,
                                                                    String description,
                                                                    City city,
                                                                    Double price
    ) {
        //given
        AdEditDto adEditDto = AdEditDto.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .description(description)
                .title(title)
                .price(price)
                .build();


        AdEntity adEntity = getAdEntity();
        given(adRepository.getById(adEditDto.getId())).willReturn(adEntity);
        //when
        adService.patchAdEntityByAdEditDto(adEditDto);
        //then
        verify(adRepository).getById(adEditDto.getId());
        verify(adRepository).save(adEntity);
        verify(adRepository).save(adEntityArgumentCaptor.capture());
        assertThat(adEntityArgumentCaptor.getValue().getAdCategory()).isEqualTo(adEditDto.getAdCategory());
        assertThat(adEntityArgumentCaptor.getValue().getCity()).isEqualTo(adEditDto.getCity());
        assertThat(adEntityArgumentCaptor.getValue().getDescription()).isEqualTo(adEditDto.getDescription());
        assertThat(adEntityArgumentCaptor.getValue().getTitle()).isEqualTo(adEditDto.getTitle());
        assertThat(adEntityArgumentCaptor.getValue().getPrice()).isEqualTo(adEditDto.getPrice());
    }

    @Test
    public void fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh_pageRequest_shouldRetrieveAdOwnerDtoPage() {
        //given
        List<AdEntity> adEntityList = user.getAdEntityList();
        adEntityList.forEach(AdEntity::postLoad);
        Page<AdEntity> userAdEntityPage = new PageImpl<>(adEntityList);
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(CREATED_DATE.getValue()).descending());
        given(adRepository.findAdEntityPageByOwnerUsername(user.getUsername(), pageRequest)).willReturn(userAdEntityPage);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.fetchAdOwnerDtoPageAndSetIsUserAbleToHighlightAndRefresh(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        verify(adRepository).findAdEntityPageByOwnerUsername(user.getUsername(), pageRequest);
        verify(userRepository).getByUsername(user.getUsername());
    }

    @Test
    public void fetchUserFavouriteAdDtoPage_pageRequest_shouldRetrieveUserFavouriteAdDtoPage() {
        //given
        List<AdEntity> adEntityList = user.getFavouriteAdEntityList();
        adEntityList.forEach(AdEntity::postLoad);
        Page<AdEntity> userFavouriteAdEntityPage = new PageImpl<>(adEntityList);
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        given(adRepository.findUserFavouriteAdEntityPageByUserId(user.getId(), pageRequest)).willReturn(userFavouriteAdEntityPage);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.fetchUserFavouriteAdDtoPage(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        verify(adRepository).findUserFavouriteAdEntityPageByUserId(user.getId(), pageRequest);
        verify(userRepository, times(2)).getByUsername(user.getUsername());
    }

    @Test
    public void fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite_usernameAndPageRequest_shouldRetrieveOwnerAdsDtoPage() {
        //given
        List<AdEntity> adEntityList = user.getAdEntityList();
        adEntityList.forEach(AdEntity::postLoad);
        Page<AdEntity> userAdEntityPage = new PageImpl<>(adEntityList);
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(CREATED_DATE.getValue()).descending());
        given(adRepository.findActiveAdEntityPageByOwnerUsername(user.getUsername(), pageRequest)).willReturn(userAdEntityPage);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.fetchAdDtoPageByOwnerUsernameAndSetUpIsUserFavourite(user.getUsername(), DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        //then
        verify(adRepository).findActiveAdEntityPageByOwnerUsername(user.getUsername(), pageRequest);
    }


    @Test
    public void fetchRandomAdDtoPage_pageSize_shouldRetrieveRandomAdsDtoPage() {
        //given
        List<AdEntity> adEntityList = getAdEntityList();
        adEntityList.forEach(AdEntity::postLoad);
        Page<AdEntity> adEntityPage = new PageImpl<>(adEntityList);
        Integer pageSize = adEntityList.size();
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        given(adRepository.findActiveRandomAdEntityPage(pageRequest)).willReturn(adEntityPage);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.fetchRandomAdDtoPage(pageSize);
        //then
        verify(adRepository).findActiveRandomAdEntityPage(pageRequest);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv", numLinesToSkip = 1)
    public void highlightAdByIdAndDeductHighlightPointFromUser_adId_shouldHighlightAd(Long id,
                                                                                      AdCategory adCategory,
                                                                                      String title,
                                                                                      String description,
                                                                                      City city,
                                                                                      Double price,
                                                                                      String ownerUsername,
                                                                                      Long countView,
                                                                                      Boolean isActive,
                                                                                      LocalDate createdDate,
                                                                                      LocalDate modifiedDate,
                                                                                      LocalDate refreshDate,
                                                                                      LocalDate expirationHighlightDate) {
        //given
        LocalDate expectedDate = LocalDate.now().plusDays(AD_HIGHLIGHTING_DURATION_IN_DAYS);
        Integer expectedUserHighlightPoints = user.getHighlightPoints() - UserUtility.POINTS_COST_OF_HIGHLIGHTING_AD;
        AdEntity adEntity = AdEntity.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .countView(countView)
                .createdDate(createdDate)
                .description(description)
                .expirationHighlightDate(expirationHighlightDate)
                .isActive(isActive)
                .modifiedDate(modifiedDate)
                .ownerUsername(ownerUsername)
                .price(price)
                .refreshDate(refreshDate)
                .title(title)
                .build();
        adEntity.postLoad();
        given(adRepository.getById(id)).willReturn(adEntity);
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.highlightAdByIdAndDeductHighlightPointFromUser(id);
        //then
        verify(userRepository).save(user);
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        Integer actualUserHighlightPoints = userEntityArgumentCaptor.getValue().getHighlightPoints();
        assertThat(actualUserHighlightPoints).isEqualTo(expectedUserHighlightPoints);

        verify(adRepository).save(adEntityArgumentCaptor.capture());
        verify(adRepository).save(adEntity);
        LocalDate actualDate = adEntityArgumentCaptor.getValue().getExpirationHighlightDate();
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void deductHighlightPointFromUser_shouldDeductHighlightPointsFromUserAccount() {
        //given
        Integer expectedUserHighlightPoints = user.getHighlightPoints() - UserUtility.POINTS_COST_OF_HIGHLIGHTING_AD;
        given(userRepository.getByUsername(user.getUsername())).willReturn(user);
        //when
        adService.deductHighlightPointFromUser();
        //then
        verify(userRepository).save(user);
        verify(userRepository).save(userEntityArgumentCaptor.capture());
        Integer actualUserHighlightPoints = userEntityArgumentCaptor.getValue().getHighlightPoints();
        assertThat(actualUserHighlightPoints).isEqualTo(expectedUserHighlightPoints);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv", numLinesToSkip = 1)
    public void changeAdStatusById_adId_shouldChangeAdStatus(Long id,
                                                             AdCategory adCategory,
                                                             String title,
                                                             String description,
                                                             City city,
                                                             Double price,
                                                             String ownerUsername,
                                                             Long countView,
                                                             Boolean isActive,
                                                             LocalDate createdDate,
                                                             LocalDate modifiedDate,
                                                             LocalDate refreshDate,
                                                             LocalDate expirationHighlightDate) {
        //given
        Boolean expectedStatus = !isActive;
        AdEntity adEntity = AdEntity.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .countView(countView)
                .createdDate(createdDate)
                .description(description)
                .expirationHighlightDate(expirationHighlightDate)
                .isActive(isActive)
                .modifiedDate(modifiedDate)
                .ownerUsername(ownerUsername)
                .price(price)
                .refreshDate(refreshDate)
                .title(title)
                .build();
        adEntity.postLoad();
        given(adRepository.getById(id)).willReturn(adEntity);
        //when
        adService.changeAdStatusById(id);
        //then
        verify(adRepository).save(adEntityArgumentCaptor.capture());
        verify(adRepository).save(adEntity);
        Boolean actualStatus = adEntityArgumentCaptor.getValue().getIsActive();
        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv", numLinesToSkip = 1)
    public void refreshAdById_adId_shouldSetNewRefreshTime(Long id,
                                                           AdCategory adCategory,
                                                           String title,
                                                           String description,
                                                           City city,
                                                           Double price,
                                                           String ownerUsername,
                                                           Long countView,
                                                           Boolean isActive,
                                                           LocalDate createdDate,
                                                           LocalDate modifiedDate,
                                                           LocalDate refreshDate,
                                                           LocalDate expirationHighlightDate) {
        //given
        LocalDate expectedDate = LocalDate.now();
        AdEntity adEntity = AdEntity.builder().id(id)
                .adCategory(adCategory)
                .city(city)
                .countView(countView)
                .createdDate(createdDate)
                .description(description)
                .expirationHighlightDate(expirationHighlightDate)
                .isActive(isActive)
                .modifiedDate(modifiedDate)
                .ownerUsername(ownerUsername)
                .price(price)
                .refreshDate(refreshDate)
                .title(title)
                .build();
        adEntity.postLoad();
        given(adRepository.getById(id)).willReturn(adEntity);
        //when
        adService.refreshAdById(id);
        //then
        verify(adRepository).save(adEntityArgumentCaptor.capture());
        verify(adRepository).save(adEntity);
        LocalDate actualDate = adEntityArgumentCaptor.getValue().getRefreshDate();
        assertThat(actualDate).isEqualTo(expectedDate);
    }
}
