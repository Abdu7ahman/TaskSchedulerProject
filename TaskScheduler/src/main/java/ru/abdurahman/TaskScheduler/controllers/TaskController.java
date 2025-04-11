package ru.abdurahman.TaskScheduler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abdurahman.TaskScheduler.dto.TaskDto;
import ru.abdurahman.TaskScheduler.service.TaskService;

import java.util.List;

@RequestMapping
@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks(@RequestParam String email) {
        return taskService.getAllTasks(email);
    }

    @PostMapping("/tasks/create")
    public TaskDto createTask(@RequestBody TaskDto taskDto, @RequestParam String email) {
        return taskService.createTask(email, taskDto);
    }

    @PutMapping("/tasks/update")
    public TaskDto updateTask(@RequestBody TaskDto taskDto, @RequestParam String email) {
        return taskService.updateTask(email, taskDto);
    }

    @DeleteMapping("/tasks/delete")
    public ResponseEntity<?> deleteTask(@RequestBody TaskDto taskDto, @RequestParam String email) {
        taskService.deleteTask(email, taskDto.getId());
        return ResponseEntity.ok().build();
    }

}
