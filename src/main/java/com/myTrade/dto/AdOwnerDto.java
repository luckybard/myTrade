package com.myTrade.dto;

import com.myTrade.utility.pojo.AdCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    private String city;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private Boolean isActive;
    private Boolean isHighlighted;
    private Long countView;
    private Boolean isRefreshable;
    private Boolean isUserAbleToHighlight;
}
