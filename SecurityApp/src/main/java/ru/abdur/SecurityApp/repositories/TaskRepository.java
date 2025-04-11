package ru.abdur.SecurityApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdur.SecurityApp.models.Project;
import ru.abdur.SecurityApp.models.Task;


import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    void deleteById(int id);


    Stream<Task> streamAllByTitleStartsWithIgnoreCase(String title);

    Stream<Task> streamAllByTitle(String title);


}
