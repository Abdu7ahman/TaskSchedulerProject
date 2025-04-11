package ru.abdurahman.TaskScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.jwt.JwtTokenUtils;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    @Autowired
    public AuthService(UserService userService, JwtTokenUtils jwtTokenUtils) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public String createTokent(UserDto authrequeste){

        String token = jwtTokenUtils
                .generateToken(userService
                .loadUserByUsername(authrequeste.getUsername()));
        return token;

    }


}
