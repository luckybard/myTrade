package com.myTrade.unitTests.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.AdEditDto;
import com.myTrade.dto.AdOwnerDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AdMapperImplTest {

    AdMapperImpl adMapper = new AdMapperImpl();

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv",numLinesToSkip = 1)
    public void shouldMapAdEntityToAdDto(Long id,
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
                                         LocalDate expirationHighlightDate
    ) {
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
        //when
        AdDto adDto = adMapper.adEntityToAdDto(adEntity);
        //then
        assertThat(adDto).isNotNull();
        assertThat(adDto).isInstanceOf(AdDto.class);
        assertThat(adDto.getId()).isEqualTo(adEntity.getId());
        assertThat(adDto.getOwnerUsername().equals(adEntity.getOwnerUsername()));
        assertThat(adDto.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(adDto.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(adDto.getCity()).isEqualTo(adEntity.getCity());
        assertThat(adDto.getModifiedDate()).isEqualTo(adEntity.getModifiedDate());
        assertThat(adDto.getCreatedDate()).isEqualTo(adEntity.getCreatedDate());
        assertThat(adDto.getAdCategory()).isEqualTo(adEntity.getAdCategory());
        assertThat(adDto.getIsActive()).isEqualTo(adEntity.getIsActive());
        assertThat(adDto.getPrice()).isEqualTo(adEntity.getPrice());
        assertThat(adDto.getIsHighlighted()).isEqualTo(adEntity.getIsHighlighted());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv",numLinesToSkip = 1)
    public void shouldMapAdEntityToAdEditDto(Long id,
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
                                             LocalDate expirationHighlightDate
    ){
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
        //when
        AdEditDto adEditDto = adMapper.adEntityToAdEditDto(adEntity);
        //then
        assertThat(adEditDto).isNotNull();
        assertThat(adEditDto).isInstanceOf(AdEditDto.class);
        assertThat(adEditDto.getId()).isEqualTo(adEntity.getId());
        assertThat(adEditDto.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(adEditDto.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(adEditDto.getCity()).isEqualTo(adEntity.getCity());
        assertThat(adEditDto.getAdCategory()).isEqualTo(adEntity.getAdCategory());
        assertThat(adEditDto.getPrice()).isEqualTo(adEntity.getPrice());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEntity.csv",numLinesToSkip = 1)
    public void shouldMapAdEntityToAdOwnerDto(Long id,
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
                                              LocalDate expirationHighlightDate
    ) {
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
        //when
        AdOwnerDto adOwnerDto = adMapper.adEntityToAdOwnerDto(adEntity);
        //then
        assertThat(adOwnerDto).isNotNull();
        assertThat(adOwnerDto).isInstanceOf(AdOwnerDto.class);
        assertThat(adOwnerDto.getId()).isEqualTo(adEntity.getId());
        assertThat(adOwnerDto.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(adOwnerDto.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(adOwnerDto.getCity()).isEqualTo(adEntity.getCity());
        assertThat(adOwnerDto.getModifiedDate()).isEqualTo(adEntity.getModifiedDate());
        assertThat(adOwnerDto.getCreatedDate()).isEqualTo(adEntity.getCreatedDate());
        assertThat(adOwnerDto.getAdCategory()).isEqualTo(adEntity.getAdCategory());
        assertThat(adOwnerDto.getIsActive()).isEqualTo(adEntity.getIsActive());
        assertThat(adOwnerDto.getPrice()).isEqualTo(adEntity.getPrice());
        assertThat(adOwnerDto.getIsHighlighted()).isEqualTo(adEntity.getIsHighlighted());
        assertThat(adOwnerDto.getIsRefreshable()).isEqualTo(adEntity.getIsRefreshable());
        assertThat(adOwnerDto.getIsUserAbleToHighlight()).isEqualTo(adEntity.getIsUserAbleToHighlight());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/adEditDto.csv",numLinesToSkip = 1)
    public void shouldMapAdEditDtoToAdEntity(Long id,
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
        //when
        AdEntity adEntity = adMapper.adEditDtoToAdEntity(adEditDto);
        //then
        assertThat(adEntity).isNotNull();
        assertThat(adEntity).isInstanceOf(AdEntity.class);
        assertThat(adEntity.getId()).isEqualTo(adEditDto.getId());
        assertThat(adEntity.getTitle()).isEqualTo(adEditDto.getTitle());
        assertThat(adEntity.getDescription()).isEqualTo(adEditDto.getDescription());
        assertThat(adEntity.getCity()).isEqualTo(adEditDto.getCity());
        assertThat(adEntity.getAdCategory()).isEqualTo(adEditDto.getAdCategory());
        assertThat(adEntity.getPrice()).isEqualTo(adEditDto.getPrice());
    }
}
