package com.company.service.impl;

import com.company.dto.TaskDTO;
import com.company.entity.Task;
import com.company.enums.Status;
import com.company.repository.TaskRepository;
import com.company.service.TaskService;
import com.company.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;



    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
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

    }

    @Override
    public void update(TaskDTO dto) {

    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long id) {
        return null;
    }


}
