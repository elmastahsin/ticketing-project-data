package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.Project;
import com.company.entity.Task;
import com.company.entity.User;
import com.company.enums.Status;
import com.company.mapper.ProjectMapper;
import com.company.mapper.UserMapper;
import com.company.repository.TaskRepository;
import com.company.service.TaskService;
import com.company.mapper.TaskMapper;
import com.company.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserService userService, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {

        Optional<Task> foundTask = taskRepository.findById(id);

        if (foundTask.isPresent()) {
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }

    }

    @Override
    public void update(TaskDTO dto) {

        Optional<Task> task = taskRepository.findById(dto.getId());
        Task convertedTask = taskMapper.convertToEntity(dto);

        if (task.isPresent()) {
            convertedTask.setTaskStatus(dto.getTaskStatus() == null ? task.get().getTaskStatus() : dto.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long id) {
        return taskRepository.findById(id).stream().map(taskMapper::convertToDTO).findFirst().get();
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {

        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {

        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity(projectDTO);
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity(projectDTO);
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDTO).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        UserDTO loggedInUser = userService.findByUserName("john@employee.com");
        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        UserDTO loggedInUser = userService.findByUserName("john@employee.com");
        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee) {
        List<Task>  tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, userMapper.convertToEntity(assignedEmployee));
        return tasks.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }


}
