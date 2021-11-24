package com.myTrade.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "conversation")

public class ConversationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String senderUsername;

    @Column(nullable = false,updatable = false)
    private String recipientUsername;

    @Column(nullable = false)
    private String title;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<MessageEntity> messageList;
}



