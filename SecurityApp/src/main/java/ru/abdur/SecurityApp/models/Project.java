package ru.abdur.SecurityApp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Project")
@Setter
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "owner")
    private List<Task> tasks;

    public Project(){

    }

    public Project(String name) {
        this.name = name;
    }
}
