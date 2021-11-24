package com.myTrade.controllers;

import com.myTrade.services.UserService;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity saveUserEntityByRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
       return userService.saveUserEntityByRegistrationRequest(registrationRequest);
    }

    @PatchMapping("/patch/favourite/add/{id}")
    public ResponseEntity addAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        return userService.addAdFromUserFavouriteAdListById(adId);
    }

    @PatchMapping("/patch/favourite/remove/{id}")
    public ResponseEntity removeAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        return userService.removeAdFromUserFavouriteAdListById(adId);
    }
}
