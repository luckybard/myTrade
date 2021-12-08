package com.myTrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Ad data is not valid")
public final class AdValidationException extends RuntimeException {
    public AdValidationException() {
        super("Ad values validation failed");
    }
}
