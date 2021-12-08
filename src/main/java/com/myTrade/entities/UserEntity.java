package com.myTrade.entities;

import com.myTrade.utility.pojo.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static com.myTrade.utility.UserUtility.INITIAL_HIGHLIGHT_POINTS;
import static com.myTrade.utility.pojo.UserRole.USER;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user")

public final class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role = USER;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<AdEntity> adEntityList;

    @ManyToMany
    private List<AdEntity> favouriteAdEntityList;

    private Integer highlightPoints = INITIAL_HIGHLIGHT_POINTS;
}
