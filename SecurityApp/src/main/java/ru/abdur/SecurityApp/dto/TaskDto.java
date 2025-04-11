package ru.abdur.SecurityApp.dto;

import ru.abdur.SecurityApp.enums.PriorityEnum;
import ru.abdur.SecurityApp.enums.StatusEnum;

public class TaskDto {

    private String title;
    private String description;
    private StatusEnum status;
    private PriorityEnum priority;

    public TaskDto(){

    }

    public TaskDto(String title, String description, StatusEnum status, PriorityEnum priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }


}
