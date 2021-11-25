package com.myTrade.dto;

import com.myTrade.entities.AdEntity;
import com.myTrade.utility.pojo.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private UserRole role = UserRole.USER;
    private List<AdDto> adList;
    private List<ConversationDto> conversationDtoList;
    private List<AdEntity> favouriteAdEntityList;
    private List<AdDto> lastViewedAdDtoQueueList;
    private Integer highlightPoint;
}
