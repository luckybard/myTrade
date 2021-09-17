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

    private Long id;

    private Long ownerId;

    private AdCategory adCategory;

    private String title;

    private String imagePath;

    private String description;

    private Double price;

    private String city;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    private Boolean isActive;

<<<<<<< HEAD
=======
    private LocalDateTime expirationHighlightTime;

    private Boolean isHighlighted;

    private LocalDateTime refreshTime;


>>>>>>> development
}
