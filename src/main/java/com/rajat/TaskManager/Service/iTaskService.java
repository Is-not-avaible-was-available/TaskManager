package com.rajat.TaskManager.Service;


import com.rajat.TaskManager.DTO.CreateTaskRequestDTO;
import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.DTO.TaskSummaryDTO;
import com.rajat.TaskManager.Exception.TaskNotFoundException;
import com.rajat.TaskManager.Mapper.TaskToTaskDTOMapper;
import com.rajat.TaskManager.Model.Priority;
import com.rajat.TaskManager.Model.Status;
import com.rajat.TaskManager.Model.Task;
import com.rajat.TaskManager.Model.TaskSpecification;
import com.rajat.TaskManager.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        task.setDeadLine(requestDTO.getDeadLine());
        task.setPriority(requestDTO.getPriority());
        task.setTags(requestDTO.getTags());

        Task newSavedTask = taskRepository.save(task);
        return taskToTaskDTOMapper.taskToResponseDTO(newSavedTask);
    }

    @Override
    public TaskResponseDTO createTask(CreateTaskRequestDTO requestDTO) {
        Task task = new Task();
        task.setDescription(requestDTO.getDescription());
        task.setTitle(requestDTO.getTitle());
        task.setDeadLine(requestDTO.getDeadLine());
        task.setPriority(requestDTO.getPriority());
        task.setTags(requestDTO.getTags());
        task.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : Status.PENDING);
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


    public List<TaskResponseDTO> filterTasks(Instant deadLine, Priority priority, Status status){
        Specification<Task> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(deadLine !=null){
            specification = specification.and(TaskSpecification.lessThanDeadLine(deadLine));
        }

        if(priority!=null){
            specification = specification.and(TaskSpecification.hasPriority(priority));
        }

        if(status!=null){
            specification = specification.and(TaskSpecification.hasStatus(status));
        }
        List<Task> tasks = taskRepository.findAll(specification);

        return tasks.stream().map(taskToTaskDTOMapper:: taskToResponseDTO).toList();
    }

    @Override
    public TaskResponseDTO markTaskAsComplete(int id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new TaskNotFoundException("Task with id: " +id+ " is not found!"));
        task.setStatus(Status.COMPLETED);
        return taskToTaskDTOMapper.taskToResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskSummaryDTO getTaskSummary() {
        List<Task> tasks = taskRepository.findAll();
        Instant now = Instant.now();

        TaskSummaryDTO taskSummaryDTO = new TaskSummaryDTO();
        taskSummaryDTO.setTotalTasks(tasks.size());
        int count = 0;
        for (Task element : tasks) {
            if (element.getStatus().equals(Status.COMPLETED)) {
                count++;
            }
        }

        taskSummaryDTO.setCompletedTasks(count);
        int priorityCount = 0;
        for (Task item : tasks) {
            if (item.getPriority().equals(Priority.HIGH) && item.getPriority()!=null) {
                priorityCount++;
            }
        }

        taskSummaryDTO.setHighPriorityCount(priorityCount);
        int pendingCount = 0;
        for (Task value : tasks) {
            if (value.getStatus().equals(Status.PENDING)) {
                pendingCount++;
            }
        }

        taskSummaryDTO.setPendingTasks(pendingCount);
        int deadLineCount = 0;
        for (Task task : tasks) {
            Instant deadLine = task.getDeadLine();
           if(deadLine.isAfter(now) && deadLine.isBefore(now.plus(1, ChronoUnit.DAYS))){
               deadLineCount++;
           }
        }

        taskSummaryDTO.setDueToday(deadLineCount);
        return taskSummaryDTO;
    }
}
