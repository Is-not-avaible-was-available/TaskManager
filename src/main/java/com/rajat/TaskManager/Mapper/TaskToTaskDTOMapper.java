package com.rajat.TaskManager.Mapper;

import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.Model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDTOMapper {

    public TaskResponseDTO taskToResponseDTO(Task task){
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        return responseDTO;
    }
}
