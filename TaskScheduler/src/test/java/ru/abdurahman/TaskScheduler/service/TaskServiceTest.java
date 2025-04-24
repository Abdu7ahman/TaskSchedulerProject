package ru.abdurahman.TaskScheduler.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.abdurahman.TaskScheduler.dto.TaskDto;
import ru.abdurahman.TaskScheduler.model.Task;
import ru.abdurahman.TaskScheduler.model.User;
import ru.abdurahman.TaskScheduler.repositories.TaskRepository;
import ru.abdurahman.TaskScheduler.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;
    private TaskDto testTaskDto;
    private final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail(TEST_EMAIL);

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(false);
        testTask.setDueDate(Timestamp.valueOf(LocalDateTime.now()));
        testTask.setUser(testUser);

        testTaskDto = new TaskDto();
        testTaskDto.setId(1L);
        testTaskDto.setTitle("Test Task");
        testTaskDto.setDescription("Test Description");
        testTaskDto.setCompleted(false);
        testTaskDto.setCompletedAt(testTask.getDueDate());
    }

    @Test
    void getAllTasks_ShouldReturnTasksList() {
        when(taskRepository.findTasksByUser_Email(TEST_EMAIL))
                .thenReturn(Optional.of(Arrays.asList(testTask)));

        List<TaskDto> result = taskService.getAllTasks(TEST_EMAIL);

        assertEquals(1, result.size());
        assertEquals(testTaskDto.getId(), result.get(0).getId());
        assertEquals(testTaskDto.getTitle(), result.get(0).getTitle());
        verify(taskRepository, times(1)).findTasksByUser_Email(TEST_EMAIL);
    }

    @Test
    void createTask_ShouldCreateAndReturnTask() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        TaskDto result = taskService.createTask(TEST_EMAIL, testTaskDto);

        assertNotNull(result);
        assertEquals(testTaskDto.getTitle(), result.getTitle());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldUpdateAndReturnTask() {
        TaskDto updatedTaskDto = new TaskDto();
        updatedTaskDto.setId(1L);
        updatedTaskDto.setTitle("Updated Task");
        updatedTaskDto.setDescription("Updated Description");
        updatedTaskDto.setCompleted(true);

        when(taskRepository.findTaskByIdAndUser_Email(1L, TEST_EMAIL))
                .thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDto result = taskService.updateTask(TEST_EMAIL, updatedTaskDto);
            
        assertNotNull(result);
        assertEquals(updatedTaskDto.getTitle(), result.getTitle());
        assertEquals(updatedTaskDto.getDescription(), result.getDescription());
        assertEquals(updatedTaskDto.isCompleted(), result.isCompleted());
        verify(taskRepository, times(1)).findTaskByIdAndUser_Email(1L, TEST_EMAIL);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_ShouldDeleteTaskAndReturnId() {
        when(taskRepository.findTaskByIdAndUser_Email(1L, TEST_EMAIL))
                .thenReturn(Optional.of(testTask));
        doNothing().when(taskRepository).delete(any(Task.class));

        Long result = taskService.deleteTask(TEST_EMAIL, 1L);

        assertEquals(1L, result);
        verify(taskRepository, times(1)).findTaskByIdAndUser_Email(1L, TEST_EMAIL);
        verify(taskRepository, times(1)).delete(any(Task.class));
    }

    @Test
    void setOwner_ShouldReturnUserByEmail() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        User result = taskService.setOwner(TEST_EMAIL);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(TEST_EMAIL, result.getEmail());
        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    }

    @Test
    void converterToDto_ShouldConvertTaskToTaskDto() {
        TaskDto result = taskService.converterToDto(testTask);

        assertNotNull(result);
        assertEquals(testTask.getId(), result.getId());
        assertEquals(testTask.getTitle(), result.getTitle());
        assertEquals(testTask.getDescription(), result.getDescription());
        assertEquals(testTask.getStatus(), result.isCompleted());
        assertEquals(testTask.getDueDate(), result.getCompletedAt());
    }

    @Test
    void converterToTask_ShouldConvertTaskDtoToTask() {
        Task result = taskService.converterToTask(testTaskDto);

        assertNotNull(result);
        assertEquals(testTaskDto.getId(), result.getId());
        assertEquals(testTaskDto.getTitle(), result.getTitle());
        assertEquals(testTaskDto.getDescription(), result.getDescription());
        assertEquals(testTaskDto.isCompleted(), result.getStatus());
        assertEquals(testTaskDto.getCompletedAt(), result.getDueDate());
    }
}