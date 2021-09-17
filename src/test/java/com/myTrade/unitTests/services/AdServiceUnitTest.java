package com.myTrade.unitTests.services;

import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.repositories.AdRepository;
import com.myTrade.services.AdService;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdServiceUnitTest {

    @Mock
    AdRepository adRepository;

    @InjectMocks
    AdService adService;

    AdMapperImpl adMapper = new AdMapperImpl();

    private AdEntity ad = new AdEntity();

    @BeforeEach
    public void setUpAd() {
        ad.setId(1L);
        ad.setOwnerId(1L);
        ad.setAdCategory(AdCategory.BOOKS);
        ad.setTitle("The Lord of the rings");
        ad.setImagePath("myTrade.com/image");
        ad.setDescription("The best book ever!");
        ad.setPrice(100.00);
        ad.setCity("Warsaw");
        ad.setIsActive(Boolean.FALSE);
    }


    @Test
    public void whenAdIdIsProvided_thenRetrievedAdIsNotNullAndBelongsToAdEntityClass() {
        //given
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        AdEntity result = adRepository.getById(1L);
        //then
        assertThat(result.getClass()).isEqualTo(AdEntity.class);
    }

//    @Test //TODO: How can i verify saved object?
//    public void whenAdEntityIsProvided_thenEntityShouldBeSavedToRepository() {
//        //given
//        //when
//        adService.saveAdDtoWithCreatedAndModifiedDateTime(adMapper.adEntityToAdDto(ad));
//        //then
//        verify(adRepository).save(ad);
//    }

  /*  @Test
    public void whenNewTitleIsProvided_thenTitleShouldBeChangedAndBeSaveToRepository() {
        //given
        String newTitle = "NewTitle";
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changeTitle(newTitle, ad.getId());
        //then
        assertThat(ad.getTitle()).isEqualTo(newTitle);
        verify(adRepository).save(ad);
    }

    @Test
    public void wheNewCategoryIsProvided_thenCategoryShouldBeChangedAndBeSaveToRepository() {
        //given
        AdCategory newAdCategory = AdCategory.FURNITURE;
        given(adRepository.getById(1L)).willReturn(ad);;
        //when
        adService.changeAdCategory(newAdCategory, ad.getId());
        //then
        assertThat(ad.getAdCategory()).isEqualTo(newAdCategory);
        verify(adRepository).save(ad);
    }

    @Test
    public void whenNewImagePathIsProvided_thenImagePathShouldBeChangedAndBeSaveToRepository() {
        //given
        String newImagePath = "NewPath";
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changeImagePath(newImagePath, ad.getId());
        //then
        assertThat(ad.getImagePath()).isEqualTo(newImagePath);
        verify(adRepository).save(ad);
    }

    @Test
    public void whenNewDescriptionIsProvided_thenDescriptionShouldBeChangedAndBeSaveToRepository() {
        //given
        String newDescription = "NewDescription";
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changeDescription(newDescription, ad.getId());
        //then
        assertThat(ad.getDescription()).isEqualTo(newDescription);
        verify(adRepository).save(ad);
    }

    @Test
    public void whenNewPriceIsProvided_thenPriceShouldBeChangedAndBeSaveToRepository() {
        //given
        Double newPrice = 100.0;
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changePrice(newPrice, ad.getId());
        //then
        assertThat(ad.getPrice()).isEqualTo(newPrice);
        verify(adRepository).save(ad);
    }

    @Test
    public void whenNewCityIsProvided_thenPriceShouldBeChangedAndBeSaveToRepository() {
        //given
        String newCity = "CityName";
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changeCity(newCity, ad.getId());
        //then
        assertThat(ad.getCity()).isEqualTo(newCity);
        verify(adRepository).save(ad);
    }

    @Test
    public void whenNewStatusIsProvided_thenStatusShouldBeChangedAndBeSaveToRepository() {
        //given
        Boolean newStatus = Boolean.TRUE;
        given(adRepository.getById(1L)).willReturn(ad);
        //when
        adService.changeActiveStatus(newStatus, ad.getId());
        //then
        assertThat(ad.getIsActive()).isEqualTo(newStatus);
        verify(adRepository).save(ad);
    }*/
}