package com.myTrade.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@AllArgsConstructor
@Data
public class RegistrationRequest {

    private String userName;
    private String password;
    private String email;
    private String avatarPath = "default/path";
    private LocalDate birthDate;
}
