package ru.abdurahman.TaskScheduler.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import ru.abdurahman.TaskScheduler.dto.UserDto;

@Component
public class JwtRequest extends UserDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}

