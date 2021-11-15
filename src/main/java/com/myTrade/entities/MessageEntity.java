package com.myTrade.entities;

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
@Table(name = "message")

public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String authorUsername;

    @Column(nullable = false)
    private String text;

    private String imagePath; //TODO: [Q] AWS for saving images? For now, just placeholder images based on adCategory

    @Column(nullable = false)
    private LocalDateTime dateTime;



}
