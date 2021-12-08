package com.myTrade.utility.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class RegistrationRequest {
    private String username;
    private String email;
    private String password;
}
