package ru.abdur.SecurityApp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.abdur.SecurityApp.enums.PriorityEnum;
import ru.abdur.SecurityApp.enums.StatusEnum;

@Entity
@Table(name = "Task")
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityEnum priority;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project owner;

    public Task(){}

    public Task(String title, String description, StatusEnum status, PriorityEnum priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

}
