package com.myTrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User is not authorized")
public final class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException() {
        super("User is not authorized to perform this action");
    }
}
