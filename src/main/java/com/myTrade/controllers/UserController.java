package com.myTrade.controllers;

import com.myTrade.services.UserService;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public final class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity saveUserEntityByRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        userService.saveUserEntityByRegistrationRequest(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/favourite/add/{id}")
    public ResponseEntity addAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        userService.addAdFromUserFavouriteAdListById(adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/favourite/remove/{id}")
    public ResponseEntity removeAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        userService.removeAdFromUserFavouriteAdListById(adId);
        return ResponseEntity.ok().build();
    }
}
