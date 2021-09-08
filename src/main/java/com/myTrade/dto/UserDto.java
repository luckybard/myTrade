package com.myTrade.dto;

import com.myTrade.utility.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto  {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private UserRole role;

    private String avatarPath = "default/path";

    private LocalDate birthDate;

    private List<AdDto> adList;

    private List<ConversationDto> conversationDtoList;

}
