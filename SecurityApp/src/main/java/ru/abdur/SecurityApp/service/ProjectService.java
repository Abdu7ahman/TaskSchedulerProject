package ru.abdur.SecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdur.SecurityApp.models.Project;
import ru.abdur.SecurityApp.repositories.ProjectRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @Transactional
    public void create(Project project){
        projectRepository.save(project);
    }
    @Transactional
    public void update(int id, String name){
        Optional<Project> project = projectRepository.findById(id);
        project.get().setName(name);
        projectRepository.save(project.get());
    }


    @Transactional
    public void delete(int id){
        projectRepository.deleteById(id);
    }











}
