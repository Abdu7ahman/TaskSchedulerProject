package ru.abdurahman.TaskScheduler.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abdurahman.TaskScheduler.dto.TaskDto;
import ru.abdurahman.TaskScheduler.service.TaskService;

import java.util.List;

@Tag(name = "Задачи", description = "Управление задачами пользователей")
@RestController
@RequestMapping
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Получить все задачи", description = "Возвращает список задач, привязанных к указанному email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)))
    })
    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks(@RequestParam
                                     @Parameter(description = "Email пользователя") String email) {
        return taskService.getAllTasks(email);
    }

    @Operation(summary = "Создать новую задачу", description = "Создаёт новую задачу для указанного email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)))
    })
    @PostMapping("/tasks/create")
    public TaskDto createTask(@RequestBody
                              @Parameter(description = "Данные задачи") TaskDto taskDto,
                              @RequestParam
                              @Parameter(description = "Email пользователя") String email) {
        return taskService.createTask(email, taskDto);
    }

    @Operation(summary = "Обновить задачу", description = "Обновляет существующую задачу по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)))
    })
    @PutMapping("/tasks/update")
    public TaskDto updateTask(@RequestBody
                              @Parameter(description = "Обновлённые данные задачи") TaskDto taskDto,
                              @RequestParam
                              @Parameter(description = "Email пользователя") String email) {
        return taskService.updateTask(email, taskDto);
    }

    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по id для указанного email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping("/tasks/delete")
    public ResponseEntity<?> deleteTask(@RequestBody
                                        @Parameter(description = "Задача с id, которую нужно удалить") TaskDto taskDto,
                                        @RequestParam
                                        @Parameter(description = "Email пользователя") String email) {
        taskService.deleteTask(email, taskDto.getId());
        return ResponseEntity.ok().build();
    }
}
