package ru.abdurahman.TaskScheduler.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private final RabbitTemplate rabbitTemplate;


    public MessageSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        String queueName = "EMAIL_SENDER";
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("Сообщение отправлено: " + message);
    }
}
