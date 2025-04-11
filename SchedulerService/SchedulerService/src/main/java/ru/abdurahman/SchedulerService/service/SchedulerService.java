package ru.abdurahman.SchedulerService.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.abdurahman.SchedulerService.dto.SummaryDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SchedulerService {
    private final RabbitMessageSender rabbitTemplate;
    private final SummaryService summaryService;

    @Autowired
    public SchedulerService(RabbitMessageSender rabbitTemplate, SummaryService summaryService) {
        this.summaryService = summaryService;
        this.rabbitTemplate = rabbitTemplate;
    }


    //@Scheduled(cron = "0 40 8 * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void sendEmails(){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp previous = Timestamp.valueOf(LocalDateTime.now().minusHours(24));

        List<SummaryDto> summaryList = summaryService.getSummaryList(now, previous);

        for (SummaryDto summary:
                summaryList) {

            rabbitTemplate.sendSummaryEmail(summary);
        }
    }
//    @Scheduled(fixedRate = 60000) // Проверка каждую минуту
//    public void notifyTasks() {
//        LocalDateTime now = LocalDateTime.now();
//        List<Task> tasks = taskRepository.findByDueDateBeforeAndIsNotifiedFalse(now);
//
//        tasks.forEach(task -> {
//            // Отправка задачи в RabbitMQ
////            rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_QUEUE, task);
////            task.setNotified(true); // Обновляем статус задачи
//            taskRepository.save(task);
//
//            log.info("Task notified: {}", task.getTitle());
//        });
//    }

}
