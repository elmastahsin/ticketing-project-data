package com.company.service;

import com.company.dto.TaskDTO;

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



}
