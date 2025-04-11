package ru.abdurahman.EmailSender.rabbitmq.listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.abdurahman.EmailSender.service.MessageProcessorService;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final MessageProcessorService messageProcessorService;

    @RabbitListener(queues = "EMAIL_SENDER")
    public void listen(Message message) {
        try {
            System.out.println("Received message: " + message.getBody());
            messageProcessorService.process(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
