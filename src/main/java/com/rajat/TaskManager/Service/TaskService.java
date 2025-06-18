package com.rajat.TaskManager.Service;

import com.rajat.TaskManager.DTO.CreateTaskRequestDTO;
import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.Exception.TaskNotFoundException;

import java.util.List;

public interface TaskService {

    public TaskResponseDTO getTask(int id) throws TaskNotFoundException;
    public List<TaskResponseDTO> getAllTask();
    public TaskResponseDTO updateTask(int id, CreateTaskRequestDTO requestDTO) throws TaskNotFoundException;
    public TaskResponseDTO createTask(CreateTaskRequestDTO requestDTO);
    public void deleteTask(int id) throws TaskNotFoundException;
}
