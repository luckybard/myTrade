package com.myTrade.dto;

import com.myTrade.utility.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private UserRole role = UserRole.USER;

    private String avatarPath = "default/path";

    private LocalDate birthDate;
    //TODO: [Q] Why after registering new user, relation tables didn't create fields with new relation mapping? Check if new ad will fix it...
    private List<AdDto> adList = new LinkedList<>();

    private List<ConversationDto> conversationDtoList = new LinkedList<>();

}
