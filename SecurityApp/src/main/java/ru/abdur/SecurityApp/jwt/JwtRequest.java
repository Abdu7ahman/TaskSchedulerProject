package ru.abdur.SecurityApp.jwt;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtRequest {
    private String username;
    private String password;
}

