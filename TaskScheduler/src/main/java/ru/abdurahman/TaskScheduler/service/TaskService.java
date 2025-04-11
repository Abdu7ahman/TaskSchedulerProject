package ru.abdurahman.TaskScheduler.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdurahman.TaskScheduler.dto.TaskDto;
import ru.abdurahman.TaskScheduler.model.Task;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.TaskRepository;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<TaskDto> getAllTasks(String email) {
        List<Task> tasks = taskRepository.findTasksByUser_Email(email).get();
        return tasks.stream().map(e -> converterToDto(e)).collect(Collectors.toList());
    }

    @Transactional
    public TaskDto createTask(String email, TaskDto taskDto) {
        Task newTask = converterToTask(taskDto);
        newTask.setUser(setOwner(email));
        taskRepository.save(newTask);
        return converterToDto(newTask);
    }

    @Transactional
    public TaskDto updateTask(String email, TaskDto taskDto) {
        Task task = taskRepository.findTaskByIdAndUser_Email(taskDto.getId(), email).get();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.isCompleted());
        task.setDueDate(now);
        taskRepository.save(task);
        return converterToDto(task);
    }

    @Transactional
    public Long deleteTask(String email, Long taskId) {
        Task task = taskRepository.findTaskByIdAndUser_Email(taskId, email).get();
        taskRepository.delete(task);
        return taskId;
    }

    public User setOwner(String email) {
        User user = userRepository.findByEmail(email).get();
        return user;
    }

    public TaskDto converterToDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setCompleted(task.getStatus());
        taskDto.setCompletedAt(task.getDueDate());
        return taskDto;
    }

    public Task converterToTask(TaskDto taskDto){
        Task task = new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getDescription(), taskDto.isCompleted(), taskDto.getCompletedAt());
        return task;
    }
}
