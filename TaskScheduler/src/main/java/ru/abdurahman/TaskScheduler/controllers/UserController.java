package ru.abdurahman.TaskScheduler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.jwt.JwtTokenUtils;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

@RequestMapping
@RestController
public class UserController {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    @Autowired
    public UserController(JwtTokenUtils jwtTokenUtils, UserRepository userRepository) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public UserDto getAllUsers(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String email = jwtTokenUtils.getUsername(jwtToken);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToUserDto(user);
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }


}
