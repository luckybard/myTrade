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
public class AdOwnerDto {
    private Long id;
    private AdCategory adCategory;
    private String title;
    private String imagePath;
    private String description;
    private Double price;
    private City city;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Boolean isActive;
    private Boolean isHighlighted;
    private Long countView;
    private Boolean isRefreshable;
    private Boolean isUserAbleToHighlight;
}
