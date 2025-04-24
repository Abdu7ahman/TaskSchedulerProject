package ru.abdurahman.TaskScheduler.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Аутентификация", description = "Регистрация и логин пользователей")
@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final MessageSenderService messageSenderService;
    private final RabbitMessageCreator rabbitMessageCreator;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService,
                          MessageSenderService messageSenderService,
                          RabbitMessageCreator rabbitMessageCreator,
                          ObjectMapper objectMapper) {
        this.authService = authService;
        this.userService = userService;
        this.messageSenderService = messageSenderService;
        this.rabbitMessageCreator = rabbitMessageCreator;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Создаёт нового пользователя и отправляет приветственное сообщение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации или пользователь уже существует",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Аутентификация пользователя", description = "Возвращает JWT токен по email и паролю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход, токен в заголовке Authorization"),
            @ApiResponse(responseCode = "401", description = "Неверные данные для входа")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        String token = authService.createTokent(jwtRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }
}
