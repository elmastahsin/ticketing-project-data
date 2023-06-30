package com.company.mapper;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.entity.Project;
import com.company.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper;


    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO dto) {
        return modelMapper.map(dto, Task.class);
    }

    public TaskDTO convertToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

}
