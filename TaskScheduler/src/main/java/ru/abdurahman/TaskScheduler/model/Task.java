package ru.abdurahman.TaskScheduler.model;
import jakarta.persistence.*;
import lombok.*;
import ru.abdurahman.TaskScheduler.enums.TaskStatus;

import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "isCompleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isCompleted;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "due_date")
    private Timestamp completedAt;

    public Task(Long id, String title, String description, boolean isCompleted, Timestamp completedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return isCompleted;
    }

    public void setStatus(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDueDate() {
        return completedAt;
    }

    public void setDueDate(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}
