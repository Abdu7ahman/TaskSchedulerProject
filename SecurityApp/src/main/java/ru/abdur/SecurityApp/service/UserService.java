package ru.abdur.SecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdur.SecurityApp.dto.UserDto;
import ru.abdur.SecurityApp.exceptions.AppError;
import ru.abdur.SecurityApp.exceptions.BadRequestException;
import ru.abdur.SecurityApp.jwt.JwtUser;
import ru.abdur.SecurityApp.models.Status;
import ru.abdur.SecurityApp.models.User;
import ru.abdur.SecurityApp.repositories.UserRepository;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity<?> create(UserDto userDto){
        User user = new User();
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        user.setUsername(userDto.getUsername());
        user.setCreated(new Date());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setStatus(Status.ACTIVE);
        user.setPassword(userDto.getPassword());
        user.setRoles(roleService.findByName("ROLE_USER").stream().toList());

        userRepository.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(String
                .format("Пользователь '%s' не найден", username)));
       return new JwtUser(
               user.getId(),
               user.getUsername(),
               user.getFirstName(),
               user.getLastName(),
               user.getPassword(),
               user.getEmail(),
               user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
       );
    }
}
