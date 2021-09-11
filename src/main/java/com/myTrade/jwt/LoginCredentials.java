package com.myTrade.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCredentials {
    private String userName;
    private String password;

    public LoginCredentials() {
    }
}
