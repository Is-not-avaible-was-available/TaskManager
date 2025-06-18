package com.rajat.TaskManager.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @LastModifiedDate
    @Column(updatable = false)
    private Instant lastModifiedAt;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = Instant.now();
        this.lastModifiedAt = Instant.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.lastModifiedAt = Instant.now();
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
