package com.myTrade.dto;

import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class AdDto {
    private Long id;
    private String ownerUsername;
    private AdCategory adCategory;
    private String title;
    private String description;
    private Double price;
    private City city;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Boolean isActive;
    private Boolean isHighlighted;
    private Long countView;
    private Boolean isUserFavourite;
}
