package com.myTrade.dto;


import com.myTrade.utility.AdCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdDto {
                            //TODO: [Q]Which values should be forbidden?
    private Long id; //X add extra id just for frontend?

    private String ownerUsername;

    private AdCategory adCategory;

    private String title;

    private String imagePath;

    private String description;

    private Double price;

    private String city;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    private Boolean isActive;

//  private LocalDateTime expirationHighlightTime;

    private Boolean isHighlighted;

    private LocalDateTime refreshTime; //X pass only boolean information about it?

    private Long countView;

    private Boolean isUserFavourite;
}
