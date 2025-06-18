package com.rajat.TaskManager.Model;


import jakarta.persistence.Entity;


@Entity

public class Task extends BaseModel{
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

