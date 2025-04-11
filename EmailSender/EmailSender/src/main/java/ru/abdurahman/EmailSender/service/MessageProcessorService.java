package ru.abdurahman.EmailSender.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import ru.abdurahman.EmailSender.dto.EmailDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MessageProcessorService {
    private final EmailSenderService emailSenderService;
    private final ObjectMapper objectMapper;

    public void process(Message message) throws IOException {
        System.out.println(message.getBody());
        String messages = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(messages);
        EmailDto emailDto = objectMapper.readValue(messages, EmailDto.class);
        emailSenderService.sendMessage(emailDto);
    }
}
