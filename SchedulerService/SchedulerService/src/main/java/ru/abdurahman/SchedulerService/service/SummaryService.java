package ru.abdurahman.SchedulerService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdurahman.SchedulerService.dto.SummaryDto;
import ru.abdurahman.SchedulerService.model.Task;
import ru.abdurahman.SchedulerService.model.User;
import ru.abdurahman.SchedulerService.repositories.TaskRepository;
import ru.abdurahman.SchedulerService.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public SummaryService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    public List<SummaryDto> getSummaryList(Timestamp now, Timestamp previous){
        List<SummaryDto> summaryList = new LinkedList<>();
        List<User> users = userRepository.findAll();

        for (User usr:
                users) {
            SummaryDto summaryDto = new SummaryDto();
            //getCompletedTodayTitles
            List<Task> completedToday = taskRepository.getTasksByUserAndCompletedAtBetween(usr, previous, now);
            List<String> completedTodayTitles = completedToday.stream()
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
            Integer completedTodayCount = completedToday.size();
            //getNotCompletedTitles
            List<Task> notCompleted = taskRepository.getTasksByUserAndIsCompleted(usr, false);
            List<String> notCompletedTitles = notCompleted.stream()
                    .map(Task::getTitle)
                    .collect(Collectors.toList());
            Integer notCompletedCount = notCompleted.size();
            //getSummaryDto
            summaryDto.setReceiverEmail(usr.getUsername());
            summaryDto.setCompletedTodayCount(completedTodayCount);
            summaryDto.setCompletedTodayTitles(completedTodayTitles);
            summaryDto.setNotCompletedCount(notCompletedCount);
            summaryDto.setNotCompletedTitles(notCompletedTitles);

            summaryList.add(summaryDto);
        }

        return summaryList;
    }
//
//    protected SummaryDto getSummaryDto(User user, List<String> completedTodayTitles, List<String> notCompletedTitles){
//        SummaryDto summaryDto = new SummaryDto();
//
//        summaryDto.setReceiverEmail(user.getUsername());
//
//        summaryDto.setCompletedTodayCount(completedTodayTitles.size());
//        summaryDto.setCompletedTodayTitles(completedTodayTitles);
//
//        summaryDto.setNotCompletedCount(notCompletedTitles.size());
//        summaryDto.setNotCompletedTitles(notCompletedTitles);
//
//        return summaryDto;
//    }
//
//    protected List<String> getCompletedTodayTitles(User user, Timestamp previous, Timestamp now){
//        List<Task> completedToday = taskRepository.getTasksByUserAndCompletedAtBetween(user, previous, now);
//        List<String> completedTodayTitles = completedToday.stream()
//                .map(Task::getTitle)
//                .collect(Collectors.toList());
//        return completedTodayTitles;
//
//    }
//
//    protected List<String> getNotCompletedTitles(User user){
//        List<Task> notCompleted = taskRepository.getTasksByUserAndCompleted(user, false);
//        List<String> notCompletedTitles = notCompleted.stream()
//                .map(Task::getTitle)
//                .collect(Collectors.toList());
//        return notCompletedTitles;
//
//    }
}
