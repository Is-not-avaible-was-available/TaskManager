package com.rajat.TaskManager.Controller;


import com.rajat.TaskManager.DTO.CreateTaskRequestDTO;
import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.Exception.TaskNotFoundException;
import com.rajat.TaskManager.Model.Priority;
import com.rajat.TaskManager.Model.Status;
import com.rajat.TaskManager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    private final TaskService taskService;
    @Autowired
    public TaskController(@Qualifier("ITaskService") TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable int id){
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        try{
           taskResponseDTO = taskService.getTask(id);
           return ResponseEntity.ok(taskResponseDTO);
        }catch (TaskNotFoundException ex){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponseDTO>> getAllTask(){
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO requestDTO){
        TaskResponseDTO responseDTO = taskService.createTask(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id,
                                                      @RequestBody CreateTaskRequestDTO requestDTO){
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        try {
            responseDTO = taskService.updateTask(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        }catch (TaskNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteTask(@PathVariable int id){
        try {
            taskService.deleteTask(id);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Task deleted successfully.");
    }
    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(@RequestParam (required = false)
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Instant deadLine,
                                                             @RequestParam (required = false) Priority priority, @RequestParam Status status){
      return ResponseEntity.ok(taskService.filterTasks(deadLine, priority));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markAsComplete(@PathVariable int id) {
        try {
            return ResponseEntity.ok(taskService.markTaskAsComplete(id));
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
