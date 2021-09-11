package com.myTrade.controllers;

import com.myTrade.dto.AdDto;
import com.myTrade.dto.ConversationDto;
import com.myTrade.dto.UserDto;
import com.myTrade.mappersImpl.AdMapperImpl;
import com.myTrade.mappersImpl.ConversationMapperImpl;
import com.myTrade.mappersImpl.UserMapperImpl;
import com.myTrade.repositories.UserRepository;
import com.myTrade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void saveUserDto(@RequestBody UserDto userDto){
        userService.saveUserEntity(userMapper.userDtoToUserEntity(userDto));
    }

    @GetMapping("/{username}")
    public UserDto fetchUserDto(@PathVariable(value = "username") String userName)  {
        return userMapper.userEntityToUserDto(userRepository.findByUserName(userName));
    }

    @GetMapping("/{username}/adList")
    public List<AdDto> fetchAdDtoList(@PathVariable(value = "username") String userName)  {
        return adMapper.adEntityListToAdDtoList(userService.findUserAdEntityList(userName));
    }

    @GetMapping("/{username}/inbox")
    public List<ConversationDto> fetchConversationDtoList(@PathVariable(value ="username")String username)  {
        return conversationMapper.conversationEntityListToConversationDtoList(userService.findUserConversationEntityList(username));
    }

    @DeleteMapping("/{username}/delete")
    public void deleteUser(@PathVariable(value = "username")String username)  {
        userService.deleteUser(username);
    }

    @PatchMapping("/{username}/adList/delete/{id}")
    public void deleteAdFromAdList(@PathVariable(value ="username") String username,@PathVariable(value = "id") Long adId) {
        userService.deleteAdFromAdList(username, adId);
    }

    @PostMapping("/{username}/adList/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAdToAdList(@PathVariable(value = "username")String username,@RequestBody AdDto adDto) {
        userService.addAdToAdList(username, adMapper.adDtoAdEntity(adDto));
    }
}
