package ru.abdurahman.TaskScheduler.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.exceptions.AppError;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //  Тест: Успешное создание пользователя
    @Test
    void testCreateUserSuccess() {
        UserDto userDto = new UserDto("testuser", "test@mail.com", "123456");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.create(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@mail.com", savedUser.getEmail());
        assertEquals("123456", savedUser.getPassword());
    }

    // Тест: Пользователь уже существует
    @Test
    void testCreateUserAlreadyExists() {
        UserDto userDto = new UserDto("testuser", "test@mail.com", "123456");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = userService.create(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof AppError);

        AppError error = (AppError) response.getBody();
        assertEquals("Пользователь с указанным именем уже существует", error.getMessage());
    }

    //  Тест: Успешная загрузка пользователя по email
    @Test
    void testLoadUserByUsernameSuccess() {
        User user = new User(1L, "testuser", "123456", "test@mail.com");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));

        User loaded = userService.loadUserByUsername("test@mail.com");

        assertEquals("testuser", loaded.getUsername());
        assertEquals("123456", loaded.getPassword());
        assertEquals("test@mail.com", loaded.getEmail());
    }

    //  Тест: Пользователь не найден
    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("notfound@mail.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("notfound@mail.com")
        );

        assertEquals("Пользователь 'notfound@mail.com' не найден", exception.getMessage());
    }
}
