package com.myTrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class MessageDto {

    private Long id;

    private Long authorId;

    private String text;

    private String imagePath;

    private LocalDateTime dateTime;



}
