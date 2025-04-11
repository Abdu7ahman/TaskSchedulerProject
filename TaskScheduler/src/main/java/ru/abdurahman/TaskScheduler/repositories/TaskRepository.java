package ru.abdurahman.TaskScheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import ru.abdurahman.TaskScheduler.model.Task;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findTasksByUser_Email(String userEmail);


    Optional<Task> findTaskByIdAndUser_Email(Long id, String  email);

}
