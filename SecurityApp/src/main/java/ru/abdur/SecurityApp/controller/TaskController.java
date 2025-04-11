package ru.abdur.SecurityApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.abdur.SecurityApp.dto.TaskDto;
import ru.abdur.SecurityApp.models.Task;
import ru.abdur.SecurityApp.repositories.TaskRepository;
import ru.abdur.SecurityApp.service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }
    @GetMapping("/projects/{id}/tasks")
    public List<TaskDto> show(){

        List<TaskDto> tasks =  taskService.findAll().stream().map(element -> converterToDto(element)).collect(Collectors.toList());
        return tasks;
    }

    @GetMapping("/search")
    @Transactional
    public List<TaskDto> fetchProjects(@RequestParam(value = "name", required = false) Optional<String> optionalPrefixName){
          optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<Task> taskStream = optionalPrefixName
                .map(taskRepository::streamAllByTitleStartsWithIgnoreCase)
               .orElseGet(null);
        Stream<TaskDto> taskDtoStream = null;

        if (optionalPrefixName.isPresent()){

            taskDtoStream = taskStream.map(element -> converterToDto(element));

        }


        return taskDtoStream.collect(Collectors.toList());
    }
    @PostMapping("/projects/{id}/add_task")
    public ResponseEntity create(@PathVariable int id, @RequestBody TaskDto taskDto){


        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setOwner(taskService.saveOwner(id));

        taskService.create(task);

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/projects/{id}/tasks/{task_id}")
    public TaskDto read(@PathVariable int task_id){
        return converterToDto(taskService.findOne(task_id));
    }
    @PostMapping("/projects/{id}/tasks/{task_id}")
    public TaskDto update(@PathVariable("task_id") int task_id, @RequestBody TaskDto taskDto){
        Optional<Task> updatedTask = taskRepository.findById(task_id);
        TaskDto taskDto1 = converterToDto(updatedTask.get());
        if(updatedTask.isEmpty()){
            return taskDto1;
        }
        updatedTask.get().setTitle(taskDto.getTitle());
        updatedTask.get().setDescription(taskDto.getDescription());
        updatedTask.get().setStatus(taskDto.getStatus());
        updatedTask.get().setPriority( taskDto.getPriority());
        taskService.create(updatedTask.get());
        return taskDto;
    }
    @DeleteMapping("projects/{id}/tasks/{task_id}")
    public void delete(@PathVariable int task_id){
        taskService.delete(task_id);
    }







    @PostMapping("/deep")
    public String index(@RequestBody String deep){
        return "ok";
    }

    @GetMapping("/people")
    @Transactional
    public String createPeople(@RequestParam("name") Optional<String> requestData) {
        Stream<Task> taskStream = requestData
                .map(taskRepository::streamAllByTitleStartsWithIgnoreCase)
                .orElseGet(null);

        if (requestData.isPresent()){
            taskStream = taskRepository.streamAllByTitleStartsWithIgnoreCase(requestData.get());
        }

        return "People created with data: " + requestData.get();
    }

    public TaskDto converterToDto(Task task){
       TaskDto taskDto = new TaskDto(task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority());
       return taskDto;
    }
    public Task converterToTask(TaskDto taskDto){
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getStatus(), taskDto.getPriority());
        return task;
    }
}
