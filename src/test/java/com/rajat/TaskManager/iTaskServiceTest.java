package com.rajat.TaskManager;

import com.rajat.TaskManager.DTO.CreateTaskRequestDTO;
import com.rajat.TaskManager.DTO.TaskResponseDTO;
import com.rajat.TaskManager.Exception.TaskNotFoundException;
import com.rajat.TaskManager.Mapper.TaskToTaskDTOMapper;
import com.rajat.TaskManager.Model.Priority;
import com.rajat.TaskManager.Model.Status;
import com.rajat.TaskManager.Model.Task;
import com.rajat.TaskManager.Repository.TaskRepository;
import com.rajat.TaskManager.Service.iTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class iTaskServiceTest {

    private TaskRepository taskRepository;
    private TaskToTaskDTOMapper taskToTaskDTOMapper;
    private iTaskService taskService;

    @BeforeEach
    public void setUp(){
        taskRepository = Mockito.mock(TaskRepository.class);
        taskToTaskDTOMapper = Mockito.mock(TaskToTaskDTOMapper.class);
        taskService = new iTaskService(taskRepository,taskToTaskDTOMapper);
    }

    @Test

    void createTask_ShouldReturnResponseDTO(){
        CreateTaskRequestDTO requestDTO = new CreateTaskRequestDTO();
        requestDTO.setTitle("Test Task");
        requestDTO.setDescription("Test Description");
        requestDTO.setStatus(Status.PENDING);
        requestDTO.setPriority(Priority.HIGH);
        requestDTO.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));
        requestDTO.setTags(List.of("urgent"));


        Task savedTask = new Task();
        savedTask.setId(1);
        savedTask.setTitle("Test Task");

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(1);
        responseDTO.setTitle("Test Task");

        when(taskToTaskDTOMapper.taskToResponseDTO(savedTask)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.createTask(requestDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    public void testGetTaskById_ShouldReturnTaskObject() throws TaskNotFoundException {
        Task task = new Task();
        task.setTitle("Test task");
        task.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));
        task.setDescription("Test description");
        task.setPriority(Priority.MEDIUM);
        task.setTags(List.of("urgent"));
        task.setStatus(Status.COMPLETED);
        task.setId(1);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(1);
        taskResponseDTO.setTitle("Test task");
        taskResponseDTO.setStatus(Status.COMPLETED);
        taskResponseDTO.setPriority(Priority.MEDIUM);
        taskResponseDTO.setDescription("Test description");
        taskResponseDTO.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));

        when(taskToTaskDTOMapper.taskToResponseDTO(task)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = taskService.getTask(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test task", result.getTitle());
        assertEquals("Test description", result.getDescription());
        assertEquals(Instant.parse("2025-06-30T18:00:00Z"), result.getDeadLine());
        assertEquals(Status.COMPLETED, result.getStatus());
        assertEquals(Priority.MEDIUM, result.getPriority());
    }

    @Test
    public void testGetByIdShould_ThrowNotFoundExceptionWhenNotFound(){
        int taskId = 99;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, ()->
                taskService.getTask(taskId));

        assertEquals("Task with id: 99 was not found!", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testGetAllTask_ShouldReturnListOfTasks(){
        Task task1 = new Task();
        task1.setTitle("Test task1");
        task1.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));
        task1.setDescription("Test description1");
        task1.setPriority(Priority.MEDIUM);
        task1.setStatus(Status.COMPLETED);
        task1.setId(1);

        Task task2 = new Task();
        task2.setTitle("Test task2");
        task2.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));
        task2.setDescription("Test description2");
        task2.setPriority(Priority.LOW);
        task2.setTags(List.of("urgent"));
        task2.setStatus(Status.COMPLETED);
        task2.setId(2);

        List<Task> tasks = List.of(task1,task2);

        when(taskRepository.findAll()).thenReturn(tasks);
        TaskResponseDTO taskResponseDTO1 = new TaskResponseDTO();
        taskResponseDTO1.setId(1);
        taskResponseDTO1.setTitle("Test task1");
        taskResponseDTO1.setStatus(Status.COMPLETED);
        taskResponseDTO1.setPriority(Priority.MEDIUM);
        taskResponseDTO1.setDescription("Test description1");
        taskResponseDTO1.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));

        TaskResponseDTO taskResponseDTO2 = new TaskResponseDTO();
        taskResponseDTO2.setId(2);
        taskResponseDTO2.setTitle("Test task2");
        taskResponseDTO2.setStatus(Status.COMPLETED);
        taskResponseDTO2.setPriority(Priority.LOW);
        taskResponseDTO2.setDescription("Test description2");
        taskResponseDTO2.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));

        when(taskToTaskDTOMapper.taskToResponseDTO(task1)).thenReturn(taskResponseDTO1);
        when(taskToTaskDTOMapper.taskToResponseDTO(task2)).thenReturn(taskResponseDTO2);

        List<TaskResponseDTO> result = taskService.getAllTask();

        assertNotNull(result);

        assertEquals(1, result.get(0).getId());
        assertEquals("Test task1", result.get(0).getTitle());

        assertEquals(2, result.get(1).getId());
        assertEquals("Test task2", result.get(1).getTitle());

        verify(taskRepository, times(1)).findAll();
        verify(taskToTaskDTOMapper, times(1)).taskToResponseDTO(task1);
        verify(taskToTaskDTOMapper, times(1)).taskToResponseDTO(task2);
    }

    @Test
    public void testGetAllTaskShouldReturnEmptyListWhenNoTaskExists(){
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskResponseDTO> taskResponseDTOS = taskService.getAllTask();

        assertNotNull(taskResponseDTOS);
        assertTrue(taskResponseDTOS.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateById_ShouldThrowErrorWhenNotFound(){
        CreateTaskRequestDTO requestDTO = new CreateTaskRequestDTO();
        requestDTO.setTitle("Test task");
        requestDTO.setDescription("Test Description");
        requestDTO.setPriority(Priority.MEDIUM);
        requestDTO.setDeadLine(Instant.parse("2025-06-30T18:00:00Z"));
        requestDTO.setStatus(Status.PENDING);
        requestDTO.setTags(List.of("medium-priority"));

        when(taskRepository.findById(999)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, ()->{
           taskService.updateTask(999,requestDTO);
        });

        assertEquals("Task with id: 999is not found!", exception.getMessage());

        verify(taskRepository, times(1)).findById(999);
    }

    @Test
    public void testUpdateTaskShouldUpdateTask() throws TaskNotFoundException {
        int taskId = 1;
        CreateTaskRequestDTO requestDTO = new CreateTaskRequestDTO();
        requestDTO.setTitle("Test task updated");
        requestDTO.setDescription("Test description updated");
        requestDTO.setStatus(Status.COMPLETED);
        requestDTO.setPriority(Priority.MEDIUM);

        Task task = new Task();
        task.setId(1);
        task.setStatus(Status.PENDING);
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setPriority(Priority.MEDIUM);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task savedTask = new Task();
        task.setId(1);
        task.setStatus(Status.COMPLETED);
        task.setTitle("Test task updated");
        task.setDescription("Test description updated");
        task.setPriority(Priority.MEDIUM);

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(1);
        responseDTO.setTitle("Test task updated");
        responseDTO.setDescription("Test description updated");
        responseDTO.setPriority(Priority.MEDIUM);
        responseDTO.setStatus(Status.COMPLETED);

        when(taskToTaskDTOMapper.taskToResponseDTO(savedTask)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.updateTask(taskId, requestDTO);

        assertEquals(1, result.getId());
        assertEquals("Test task updated", result.getTitle());
        assertEquals("Test description updated", result.getDescription());
        assertEquals(Priority.MEDIUM, result.getPriority());
        assertEquals(Status.COMPLETED, result.getStatus());

        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testDeleteById_ShouldThrowErrorWhenNoTaskFound(){
        int id = 999;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class,
                ()-> taskService.deleteTask(id));
        assertEquals("Task with id: 999is not found!", ex.getMessage());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteById_ShouldDeleteTheTask() throws TaskNotFoundException {
        int id = 10;

        Task task = new Task();
        task.setId(id);
        task.setStatus(Status.COMPLETED);
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setPriority(Priority.HIGH);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        taskService.deleteTask(id);
        verify(taskRepository, times(1)).delete(task);
    }
}
