package com.myTrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConversationDto {
    private Long id;
    private String senderUsername;
    private String recipientUsername;
    private String title;
    private List<MessageDto> messageDtoList;
}
