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
public final class MessageDto {
    private Long id;
    private String authorUsername;
    private String text;
    private LocalDateTime dateTime;
}
