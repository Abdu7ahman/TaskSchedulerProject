package ru.abdurahman.EmailSender.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private final JavaMailSender emailSender;
    @Autowired
    public EmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    public void sendSimpleMessage(SimpleMailMessage message) {
        emailSender.send(message);
    }
}