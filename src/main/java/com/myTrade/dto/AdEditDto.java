package com.myTrade.dto;

import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdEditDto {
    private Long id;
    private AdCategory adCategory;
    private String title;
    private String description;
    private Double price;
    private City city;
}
