package com.rajat.TaskManager.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;


@Entity

public class Task extends BaseModel{
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant deadLine;
    @ElementCollection
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public void setStatus(Status status){
        this.status = status;
    }
    public Status getStatus(){
        return status;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    public Priority getPriority(){
        return priority;
    }

    public void setDeadLine(Instant deadLine){
        this.deadLine = deadLine;
    }

    public Instant getDeadLine(){
        return deadLine;
    }

    public void setTags(List<String> tags){
        this.tags = tags;
    }

    public List<String> getTags(){
        return tags;
    }
}

