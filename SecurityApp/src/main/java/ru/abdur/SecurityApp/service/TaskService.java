package ru.abdur.SecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdur.SecurityApp.models.Project;
import ru.abdur.SecurityApp.models.Task;
import ru.abdur.SecurityApp.repositories.ProjectRepository;
import ru.abdur.SecurityApp.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }
    @Transactional
    public Task findOne(int id){
        Optional<Task> foundTask = taskRepository.findById(id);
        return foundTask.orElse(null);
    }
    @Transactional
    public void create(Task task){

        taskRepository.save(task);
    }
    @Transactional
    public void update(int id, Task updatedTask){
        updatedTask.setId(id);
        taskRepository.save(updatedTask);
    }
    @Transactional
    public void delete(int id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public Project saveOwner(int id){
        Project project = projectRepository.findProjectById(id);
        return project;

    }

}
