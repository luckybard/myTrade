package com.myTrade.integrationTests.service;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.repositories.AdRepository;
import com.myTrade.services.AdService;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdServiceTest {

    private AdService adService;
    private AdRepository adRepository;

    @Autowired
    public AdServiceTest(AdService adService, AdRepository adRepository) {
        this.adService = adService;
        this.adRepository = adRepository;
    }

    private AdEntity adEntity;

    @BeforeEach
    public void setUpAdEntity() {
        adEntity = adRepository.getById(1L);
    }

    @Test
    void saveAdDtoWithCreatedAndModifiedDateTime() {
        //given
        String titleAsAKeyToFindAd = "The Lord of the 123";
        AdDto ad = new AdDto();
        ad.setOwnerId(1L);
        ad.setAdCategory(AdCategory.BOOKS);
        ad.setTitle(titleAsAKeyToFindAd);
        ad.setImagePath("myTrade.com/image");
        ad.setDescription("The best book ever!");
        ad.setPrice(100.00);
        ad.setCity("Warsaw");
        ad.setIsActive(Boolean.FALSE);
        //when
        adService.saveAdDtoWithCreatedAndModifiedDateTime(ad);
        //then
        AdEntity actual = adRepository.findAll().stream().filter(adEntity -> adEntity.getTitle().equalsIgnoreCase(titleAsAKeyToFindAd)).collect(Collectors.toList()).get(0);
        assertThat(actual).isNotNull();
        assertThat(actual.getCreatedDateTime()).isNotNull();
        assertThat(actual.getModifiedDateTime()).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @Transactional
    void changeTitle() {
        //given
        String newTitle = "NewTitle";
        //when
        adService.changeTitle(newTitle,adEntity.getId() );
        assertThat(adRepository.getById(1L).getTitle()).isEqualTo(newTitle);

    }

    @Test
    @Transactional
    void changeAdCategory() {
        //given
        AdCategory newAdCategory = AdCategory.FURNITURE;
        //when
        adService.changeAdCategory(newAdCategory, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getAdCategory()).isEqualTo(newAdCategory);
    }

    @Test
    @Transactional
    void changeImagePath() {
        //given
        String newImagePath = "NewPath";
        //when
        adService.changeImagePath(newImagePath, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getImagePath()).isEqualTo(newImagePath);
    }

    @Test
    @Transactional
    void changeDescription() {
        //given
        String newDescription = "NewDescription";
        //when
        adService.changeDescription(newDescription, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getDescription()).isEqualTo(newDescription);
    }

    @Test
    @Transactional
    void changePrice() {
        //given
        Double newPrice = 100.0;
        //when
        adService.changePrice(newPrice, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getPrice()).isEqualTo(newPrice);
    }

    @Test
    @Transactional
    void changeCity() {
        //given
        String newCity = "CityName";
        //when
        adService.changeCity(newCity, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getCity()).isEqualTo(newCity);

    }

    @Test
    @Transactional
    void changeActiveStatus() {
        //given
        Boolean newStatus = Boolean.TRUE;
        //when
        adService.changeActiveStatus(newStatus, adEntity.getId());
        //then
        assertThat(adRepository.getById(1L).getIsActive()).isEqualTo(newStatus);

    }
}