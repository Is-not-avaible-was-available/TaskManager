package com.rajat.TaskManager.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequestDTO {

    private String title;
    private String description;

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
}
