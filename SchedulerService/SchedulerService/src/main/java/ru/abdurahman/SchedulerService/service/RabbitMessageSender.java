package ru.abdurahman.SchedulerService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.abdurahman.SchedulerService.dto.EmailDto;
import ru.abdurahman.SchedulerService.dto.SummaryDto;

@Component
@RequiredArgsConstructor
public class RabbitMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMessageCreator rabbitMessageCreator;
    private final ObjectMapper objectMapper;

    public void sendSummaryEmail(SummaryDto summaryDto){
        String queueName = "EMAIL_SENDER";
        EmailDto emailDto = rabbitMessageCreator.createSummaryMessage(summaryDto);
        sendEmail(emailDto, queueName);
    }


    protected void sendEmail(EmailDto emailDto, String queueName){
        try {
            rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(emailDto));
        }catch (Exception e){

        }
    }


}