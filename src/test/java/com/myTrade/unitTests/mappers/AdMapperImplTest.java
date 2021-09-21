package com.myTrade.unitTests.mappers;

import com.myTrade.dto.AdDto;
import com.myTrade.entities.AdEntity;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.utility.AdCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AdMapperImplTest {

    AdMapperImpl adMapper = new AdMapperImpl();

    AdEntity adEntity = new AdEntity();

    @BeforeEach
    public void setUpAdEntity(){
        adEntity.setId(1L);
        adEntity.setOwnerUsername("john");
        adEntity.setAdCategory(AdCategory.BOOKS);
        adEntity.setTitle("The Lord of the rings");
        adEntity.setImagePath("image/path");
        adEntity.setDescription("The best book");
        adEntity.setPrice(40.00);
        adEntity.setCity("Warsaw");
        adEntity.setCreatedDateTime(LocalDateTime.of(LocalDate.of(2020, 8, 21), LocalTime.of(20, 18)));
        adEntity.setModifiedDateTime(LocalDateTime.now());
    }

    @Test
    public void shouldMapToDtoWithNestedObjects(){
        //given
        //when
        AdDto adDto = adMapper.adEntityToAdDto(adEntity);
        //then
        assertThat(adDto).isNotNull();
        assertThat(adDto).isInstanceOf(AdDto.class);
        assertThat(adDto.getId()).isEqualTo(adEntity.getId());
        assertThat(adDto.getOwnerUsername().equals(adEntity.getOwnerUsername()));
        assertThat(adDto.getTitle()).isEqualTo(adEntity.getTitle());
        assertThat(adDto.getDescription()).isEqualTo(adEntity.getDescription());
        assertThat(adDto.getImagePath()).isEqualTo(adEntity.getImagePath());
        assertThat(adDto.getCity()).isEqualTo(adEntity.getCity());
        assertThat(adDto.getModifiedDateTime()).isEqualTo(adEntity.getModifiedDateTime());
        assertThat(adDto.getCreatedDateTime()).isEqualTo(adEntity.getCreatedDateTime());
        assertThat(adDto.getAdCategory()).isEqualTo(adEntity.getAdCategory());
        assertThat(adDto.getIsActive()).isEqualTo(adEntity.getIsActive());
        assertThat(adDto.getPrice()).isEqualTo(adEntity.getPrice());
    }

    @Test
    public void shouldMapToEntityWithNestedObjects(){
        //given
        AdDto adDto = adMapper.adEntityToAdDto(adEntity);
        //when
        AdEntity adEntity = adMapper.adDtoAdEntity(adDto);
        //then
        assertThat(adEntity).isNotNull();
        assertThat(adEntity).isInstanceOf(AdEntity.class);
        assertThat(adEntity.getId()).isEqualTo(adEntity.getId());
        assertThat(adEntity.getOwnerUsername()).isEqualTo(adEntity.getOwnerUsername());
        assertThat(adEntity.getTitle()).isEqualTo(adDto.getTitle());
        assertThat(adEntity.getDescription()).isEqualTo(adDto.getDescription());
        assertThat(adEntity.getImagePath()).isEqualTo(adDto.getImagePath());
        assertThat(adEntity.getCity()).isEqualTo(adDto.getCity());
        assertThat(adEntity.getModifiedDateTime()).isEqualTo(adDto.getModifiedDateTime());
        assertThat(adEntity.getCreatedDateTime()).isEqualTo(adDto.getCreatedDateTime());
        assertThat(adEntity.getAdCategory()).isEqualTo(adDto.getAdCategory());
        assertThat(adEntity.getIsActive()).isEqualTo(adDto.getIsActive());
        assertThat(adEntity.getPrice()).isEqualTo(adDto.getPrice());
    }

}
