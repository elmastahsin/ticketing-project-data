package com.company.service;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.enums.Status;

import java.util.List;

public interface TaskService {
    //save
    void save(TaskDTO task);
    //delete
    void delete(Long id);

    //update
    void update(TaskDTO dto);
    //findAll
    List<TaskDTO> findAll();
    //findById
    TaskDTO findById(Long id);


    int totalNonCompletedTasks(String projectCode);

    int totalCompletedTasks(String projectCode);

    void deleteByProject(ProjectDTO projectDTO);

    void completeByProject(ProjectDTO projectDTO);


    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByStatus(Status status);
}
