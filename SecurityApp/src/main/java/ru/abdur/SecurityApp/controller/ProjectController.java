package ru.abdur.SecurityApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abdur.SecurityApp.dto.ProjectDto;
import ru.abdur.SecurityApp.exceptions.BadRequestException;
import ru.abdur.SecurityApp.models.Project;
import ru.abdur.SecurityApp.models.Task;
import ru.abdur.SecurityApp.repositories.ProjectRepository;
import ru.abdur.SecurityApp.service.ProjectService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;



    public static final String GET_PROJECTS = "/projects/{id}";
    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }
    @GetMapping("/projects")
    public List<ProjectDto> show(){
        return projectService.findAll().stream().map(element -> convertToProjectDto(element)).collect(Collectors.toList());

    }

    @GetMapping("/projects/{id}")
    public ProjectDto findProject(@PathVariable int id){


        Project project = projectRepository.findProjectById(id);
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        return projectDto;

    }
    @PostMapping("/projects/{id}/update")
    public ProjectDto update(@PathVariable int id, @RequestBody ProjectDto projectDto){
        Project project = projectRepository.findProjectById(id);
        project.setName(projectDto.getName());
        projectService.create(project);
        return projectDto;
    }
    @PostMapping("/projects/add")
    public ResponseEntity create(@RequestBody Optional<ProjectDto> projectDto){
        Optional<Project> project = projectRepository.findProjectByName(projectDto.get().getName());
        if (project.isPresent()){
            throw new BadRequestException(String.format("project // already exist", projectDto.get().getName()));
        }
        Project createdProject = convertToProject(projectDto.get());
        projectService.create(createdProject);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    public Project convertToProject(ProjectDto projectDto){
        return new Project(projectDto.getName());
    }
    public ProjectDto convertToProjectDto(Project project){
        ProjectDto projectDto = new ProjectDto(project.getName());
        return projectDto;
    }















}
