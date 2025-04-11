package ru.abdur.SecurityApp.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.abdur.SecurityApp.models.Status;

import java.util.Date;
@Data
@RequiredArgsConstructor
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
