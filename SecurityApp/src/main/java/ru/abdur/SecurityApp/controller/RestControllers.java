package ru.abdur.SecurityApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abdur.SecurityApp.dto.UserDto;
import ru.abdur.SecurityApp.jwt.JwtRequest;
import ru.abdur.SecurityApp.jwt.JwtResponse;
import ru.abdur.SecurityApp.service.AuthService;
import ru.abdur.SecurityApp.service.UserService;

@RestController
@RequestMapping
public class RestControllers {
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public RestControllers(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/auth")
    public JwtResponse createAuth(@RequestBody JwtRequest authRequest){
        String token = authService.createTokent(authRequest);
        return new JwtResponse(token);
    }
    @PostMapping("/registr")
    public ResponseEntity addUser(@RequestBody UserDto userDto){
        userService.create(userDto);
        //String las = userDto.getLastName();

        return ResponseEntity.ok(HttpStatus.OK) ;
    }
    @PostMapping("/gett")
    public String gett(){
        return "fuck you";
    }
}
