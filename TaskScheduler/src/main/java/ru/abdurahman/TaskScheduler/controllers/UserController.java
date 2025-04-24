package ru.abdurahman.TaskScheduler.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.jwt.JwtTokenUtils;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

@Tag(name = "Пользователи", description = "Получение информации о текущем пользователе")
@RestController
@RequestMapping
public class UserController {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    @Autowired
    public UserController(JwtTokenUtils jwtTokenUtils, UserRepository userRepository) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Получить текущего пользователя", description = "Возвращает данные пользователя на основе JWT токена из заголовка")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Токен недействителен или отсутствует", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
    })
    @GetMapping("/user")
    public UserDto getAllUsers(
            @RequestHeader("Authorization")
            @Parameter(description = "JWT токен. Пример: Bearer eyJhbGci...") String token) {

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
