package ru.abdurahman.TaskScheduler.service;

import org.springframework.stereotype.Service;
import ru.abdurahman.TaskScheduler.dto.EmailDto;

@Service
public class RabbitMessageCreator {
    public EmailDto createWelcomeMessage(String receiverEmail) {
        EmailDto emailDto = new EmailDto();
        emailDto.setReceiverEmail(receiverEmail);
        emailDto.setSubject("WELCOME");
        emailDto.setBody("WELCOME");
        return emailDto;
    }
}