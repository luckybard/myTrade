package com.myTrade.entities;

import com.myTrade.utility.pojo.AdCategory;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime modifiedDateTime;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private LocalDateTime expirationHighlightTime;

    @Column(nullable = false)
    private LocalDateTime refreshTime;

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
        if(LocalDateTime.now().isBefore(expirationHighlightTime)){
            setIsHighlighted(true);
        }
    }
}
