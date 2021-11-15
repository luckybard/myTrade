package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.UserDto;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    private final AdMapperImpl adMapper;
    private final ConversationMapperImpl conversationMapper;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = new UserMapperImpl();
        this.adMapper = new AdMapperImpl();
        this.conversationMapper = new ConversationMapperImpl();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUserDto(@RequestBody UserDto userDto) {
        userService.saveUserEntity(userMapper.userDtoToUserEntity(userDto));
    }

//    @GetMapping("/{username}")
//    public UserDto fetchUserDto(@PathVariable(value = "username") String userName) {
//        return userMapper.userEntityToUserDto(userRepository.findByUsername(userName));
//    }

    @GetMapping("/adList")
    public Page<AdDto> fetchAdDtoList(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
        return userService.findUserAdEntityList(pageNumber.orElse(0), pageSize.orElse(10));
    }

    @GetMapping("/{username}")
    public Page<AdDto> fetchAdDtoList(@PathVariable(value = "username") String username,
                                      @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
        return userService.findUserAdEntityList(username, pageNumber.orElse(0), pageSize.orElse(10));
    }

    @GetMapping("/favourite")
    public Page<AdDto> fetchFavouriteAdDtoList(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
        return userService.findUserFavouriteAdEntityList(pageNumber.orElse(0), pageSize.orElse(10));
    }

    @DeleteMapping("/delete")
    public void deleteUser() {
        userService.deleteUser();
    }

    @PatchMapping("/favourite/{id}")
    public void addOrRemoveFromFavouriteList(@PathVariable(value = "id") Long adId){
        userService.addOrRemoveFromFavouriteList(adId);
    }
}
