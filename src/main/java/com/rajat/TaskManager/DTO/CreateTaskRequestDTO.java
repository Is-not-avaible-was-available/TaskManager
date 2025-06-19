package com.rajat.TaskManager.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.rajat.TaskManager.Model.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CreateTaskRequestDTO {

    private String title;
    private String description;
    private Priority priority;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant deadLine;
    private List<String> tags;

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
