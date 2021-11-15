package com.myTrade.entities;

import com.myTrade.utility.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user")

public class UserEntity {
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
    private UserRole role;

    private String avatarPath = "default/path";

    private LocalDate birthDate;

    @OneToMany
    private List<AdEntity> adEntityList = new LinkedList<>();

    @ManyToMany
    private List<ConversationEntity> conversationEntityList = new LinkedList<>();

    @ManyToMany //TODO: [Q] java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '1' for key 'user_favourite_ad_entity_list.UK_iftkoyxv2utjlvjl9ayu4nc0g' (when was OnetoMany)
    private List<AdEntity> favouriteAdEntityList = new LinkedList<>();

    @OneToMany
    private List<AdEntity> lastViewedAdEntityQueueList = new LinkedList<>(); //TODO: Change to LIFO queue

    private Integer highlightPoint;
}
