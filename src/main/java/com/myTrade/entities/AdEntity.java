package com.myTrade.entities;


import com.myTrade.utility.AdCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private LocalDateTime expirationHighlightTime;

    @Column(nullable = false)
    private LocalDateTime refreshTime;

    @Transient
    private Boolean isHighlighted;

    @PostLoad
    public void checkIsHighlighted(){
        if(expirationHighlightTime.isBefore(LocalDateTime.now())){
            setIsHighlighted(true);
        }
    }
}
