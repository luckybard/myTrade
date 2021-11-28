package com.myTrade.entities;

import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static com.myTrade.utility.AdUtility.INITIAL_AD_VIEW_COUNT;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = false)
    private LocalDate modifiedDate;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private LocalDate expirationHighlightDate;

    @Column(nullable = false)
    private LocalDate refreshDate;

    @Column(nullable = false)
    private Long countView = INITIAL_AD_VIEW_COUNT;

    @Transient
    private Boolean isHighlighted = false;

    @Transient
    private Boolean isUserFavourite = false;

    @Transient
    private Boolean isRefreshable = false;

    @Transient
    private Boolean isUserAbleToHighlight = false;

    @PostLoad
    public void postLoad(){
        if(LocalDate.now().isBefore(expirationHighlightDate)){
            setIsHighlighted(true);
        }
    }
}
