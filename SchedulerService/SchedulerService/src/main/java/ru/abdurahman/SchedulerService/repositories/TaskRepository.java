package ru.abdurahman.SchedulerService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdurahman.SchedulerService.model.Task;
import ru.abdurahman.SchedulerService.model.User;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByUserAndCompletedAtBetween(User user, Timestamp previous, Timestamp now);
    List<Task> getTasksByUserAndIsCompleted(User user, boolean completed);

}
