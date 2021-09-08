package com.myTrade.services;

import com.myTrade.utility.RegistrationRequest;
import com.myTrade.utility.validators.RegistrationRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;
    private RegistrationRequestValidator requestValidator;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserService userService, RegistrationRequestValidator requestValidator, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.requestValidator = requestValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationRequest request){
       if(requestValidator.test(request)){
           request.setPassword(passwordEncoder.encode(request.getPassword()));
           userService.singUpUserByRegistrationRequest(request);
           //TODO: send confirmation token?!
       }


    }
}
