package ru.abdurahman.TaskScheduler.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abdurahman.TaskScheduler.dto.EmailDto;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.jwt.JwtRequest;
import ru.abdurahman.TaskScheduler.service.AuthService;
import ru.abdurahman.TaskScheduler.service.MessageSenderService;
import ru.abdurahman.TaskScheduler.service.RabbitMessageCreator;
import ru.abdurahman.TaskScheduler.service.UserService;

@RequestMapping
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final MessageSenderService messageSenderService;
    private final RabbitMessageCreator rabbitMessageCreator;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthController(AuthService authService, UserService userService, MessageSenderService messageSenderService, RabbitMessageCreator rabbitMessageCreator, ObjectMapper objectMapper) {
        this.authService = authService;
        this.userService = userService;
        this.messageSenderService = messageSenderService;
        this.rabbitMessageCreator = rabbitMessageCreator;
        this.objectMapper = objectMapper;
    }
    @PostMapping("/user")
    public ResponseEntity<?> registration(@RequestBody @Valid UserDto userDto) throws JsonProcessingException {
        userService.create(userDto);
        String token = authService.createTokent(userDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);


        EmailDto emailDto = rabbitMessageCreator.createWelcomeMessage(userDto.getEmail());
        messageSenderService.sendMessage(objectMapper.writeValueAsString(emailDto));

        return ResponseEntity.ok().headers(headers).body("User registered successfully");
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody  JwtRequest jwtRequest) {
        String token = authService.createTokent(jwtRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }
}



