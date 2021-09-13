package com.myTrade.entities;


import com.myTrade.utility.AdCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long ownerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdCategory adCategory;

    @Column(nullable = false)
    private String title;

    private String imagePath;

    @Column(nullable = false)
    private String description;

    private Double price;

    private String city;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime modifiedDateTime;

    @Column(nullable = false)
    private Boolean isActive;

    private LocalDateTime expirationHighlightTime;

    @Transient
    private Boolean isHighlighted;

    private LocalDateTime refreshTime;

    public AdEntity(Long id, Long ownerId, AdCategory adCategory, String title, String imagePath,
                    String description, Double price, String city, LocalDateTime createdDateTime,
                    LocalDateTime modifiedDateTime, Boolean isActive, LocalDateTime expirationHighlightTime,
                    Boolean isHighlighted, LocalDateTime refreshTime) {
        this.id = id;
        this.ownerId = ownerId;
        this.adCategory = adCategory;
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.city = city;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.isActive = isActive;
        this.expirationHighlightTime = expirationHighlightTime;
        this.isHighlighted = expirationHighlightTime.isBefore(LocalDateTime.now());
        this.refreshTime = refreshTime;
    }
}
