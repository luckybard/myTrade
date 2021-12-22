package com.myTrade.controllers;

import com.myTrade.services.UserService;
import com.myTrade.utility.pojo.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
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
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity addAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        userService.addAdFromUserFavouriteAdListById(adId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/points")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity fetchUserHighlightPoints(){
        Integer userHighlightPoints = userService.fetchUserHighlightPoints();
        return ResponseEntity.ok(userHighlightPoints);
    }

    @PatchMapping("/favourite/remove/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity removeAdFromUserFavouriteAdListById(@PathVariable(value = "id") Long adId) {
        userService.removeAdFromUserFavouriteAdListById(adId);
        return ResponseEntity.ok().build();
    }
}
