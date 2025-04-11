package ru.abdur.SecurityApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdur.SecurityApp.models.Project;


import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findProjectById(int id);
    Optional<Project> findProjectByName(String name);
}
