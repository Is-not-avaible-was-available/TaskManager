package com.rajat.TaskManager.Service;


import com.rajat.TaskManager.DTO.CreateTaskRequestDTO;
import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.Exception.TaskNotFoundException;
import com.rajat.TaskManager.Mapper.TaskToTaskDTOMapper;
import com.rajat.TaskManager.Model.Task;
import com.rajat.TaskManager.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ITaskService")
public class iTaskService implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskToTaskDTOMapper taskToTaskDTOMapper;

    @Autowired
    public iTaskService(TaskRepository taskRepository, TaskToTaskDTOMapper taskToTaskDTOMapper){
        this.taskRepository = taskRepository;
        this.taskToTaskDTOMapper = taskToTaskDTOMapper;
    }

    @Override
    public TaskResponseDTO getTask(int id) throws TaskNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " + id + " was not found!");
        }
        Task task = optionalTask.get();
        return taskToTaskDTOMapper.taskToResponseDTO(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTask() {
        List<Task> tasks  = taskRepository.findAll();
        List<TaskResponseDTO> responseDTOS= new ArrayList<>();
        for(Task task : tasks){
            responseDTOS.add(taskToTaskDTOMapper.taskToResponseDTO(task));
        }
        return responseDTOS;
    }

    @Override
    public TaskResponseDTO updateTask(int id, CreateTaskRequestDTO requestDTO) throws TaskNotFoundException{
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " +id+ "is not found!");
        }

        Task task = optionalTask.get();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());

        Task newSavedTask = taskRepository.save(task);
        return taskToTaskDTOMapper.taskToResponseDTO(newSavedTask);
    }

    @Override
    public TaskResponseDTO createTask(CreateTaskRequestDTO requestDTO) {
        Task task = new Task();
        task.setDescription(requestDTO.getDescription());
        task.setTitle(requestDTO.getTitle());
        Task savedTask = taskRepository.save(task);
        return taskToTaskDTOMapper.taskToResponseDTO(savedTask);
    }

    @Override
    public void deleteTask(int id)throws TaskNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " +id+ "is not found!");
        }
        taskRepository.delete(optionalTask.get());
    }
}
