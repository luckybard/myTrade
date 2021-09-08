package com.myTrade.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class ConversationDto {

    private Long id;

    private Long senderId;

    private Long recipientId;

    private String title;

    private List<MessageDto> messageDtoList = new LinkedList<>();
}
