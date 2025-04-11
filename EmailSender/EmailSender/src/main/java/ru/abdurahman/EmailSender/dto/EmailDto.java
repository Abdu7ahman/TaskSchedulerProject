package ru.abdurahman.EmailSender.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    @Email
    private String receiverEmail;
    private String subject;
    private String body;
}