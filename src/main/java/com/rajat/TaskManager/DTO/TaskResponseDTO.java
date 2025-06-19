package com.rajat.TaskManager.DTO;


import com.rajat.TaskManager.Model.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private String title;
    private String description;
    private int id;
    private Priority priority;
    private Instant deadLine;
    private List<String> tags;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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
