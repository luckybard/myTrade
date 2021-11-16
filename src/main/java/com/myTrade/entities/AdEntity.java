package com.myTrade.entities;


import com.myTrade.utility.AdCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ad")

public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    @Column(nullable = false,updatable = false)
    private String ownerUsername;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdCategory adCategory;

    @Column(nullable = false)
    private String title;

    private String imagePath;

    @Column(nullable = false)
    private String description;

    private Double price;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime modifiedDateTime;

    @Column(nullable = false)
    private Boolean isActive;

    private LocalDateTime expirationHighlightTime;

    private LocalDateTime refreshTime;

    private Long countView;

    @Transient
    private Boolean isHighlighted = false;;

    @Transient
    private Boolean isUserFavourite = false;

    @Transient
    private Boolean isRefreshable = false;;

    @Transient
    private Boolean isHighlightable = false; //TODO: [Q] Name should be change, similar to isHighlighted. Create UserAdDto is good idea? For edit purpose?

    @PostLoad
    public void postLoad(){
        if(LocalDateTime.now().isBefore(expirationHighlightTime)){
            setIsHighlighted(true);
        }
//        else setIsHighlighted(false);
        if(LocalDateTime.now().isAfter(refreshTime.plusDays(7))){ //TODO:[Q]
            setIsRefreshable(true);
        }
//        else setIsRefreshable(false);
    }
}
