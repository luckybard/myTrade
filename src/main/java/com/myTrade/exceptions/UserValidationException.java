package com.myTrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User data is not valid")
public final class UserValidationException extends RuntimeException {
    public UserValidationException() {
        super("User values validation failed");
    }
}
