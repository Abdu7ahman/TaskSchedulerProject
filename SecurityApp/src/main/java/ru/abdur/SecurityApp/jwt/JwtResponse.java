package ru.abdur.SecurityApp.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}

