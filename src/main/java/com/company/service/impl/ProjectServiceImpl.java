package com.company.service.impl;


import com.company.dto.ProjectDTO;
import com.company.dto.UserDTO;
import com.company.entity.Project;
import com.company.entity.User;
import com.company.enums.Status;
import com.company.mapper.ProjectMapper;
import com.company.mapper.UserMapper;
import com.company.repository.ProjectRepository;
import com.company.service.ProjectService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;



    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO getByProjectCode(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        return projectMapper.convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> list = projectRepository.findAll(Sort.by("projectCode"));

        return list.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        UserDTO currentUserDto = userService.findByUserName("harold@manager.com");
        User user = userMapper.convertToEntity(currentUserDto);
        List<Project> projects = projectRepository.findAllByAssignedManager(user);
        return projects.stream().map(project -> {
            ProjectDTO obj = projectMapper.convertToDTO(project);
            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());


    }
}
