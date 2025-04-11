package ru.abdurahman.EmailSender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.abdurahman.EmailSender.dto.EmailDto;
import ru.abdurahman.EmailSender.mail.EmailSender;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final EmailSender emailSender;

    private final String senderEmail = "batyrmurzaevumar@gmail.com";

    public void sendMessage(EmailDto emailDto){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(emailDto.getReceiverEmail());
        System.out.println(emailDto.getReceiverEmail());
        message.setSubject(emailDto.getSubject());
        System.out.println(emailDto.getSubject());
        message.setText(emailDto.getBody());
        System.out.println(emailDto.getBody());
        emailSender.sendSimpleMessage(message);
    }
}



