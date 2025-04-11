package ru.abdurahman.TaskScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdurahman.TaskScheduler.dto.UserDto;
import ru.abdurahman.TaskScheduler.exceptions.AppError;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<?> create(UserDto userDto){
        User user = new User();
        if (userRepository.findByUsername(userDto.getUsername()).isPresent() &&
            userRepository.findByEmail(userDto.getEmail()).isPresent()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->  new UsernameNotFoundException(String
                        .format("Пользователь '%s' не найден", email)));
        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }


}
