package com.company.service.impl;


import com.company.dto.ProjectDTO;
import com.company.entity.Project;
import com.company.enums.Status;
import com.company.mapper.ProjectMapper;
import com.company.repository.ProjectRepository;
import com.company.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public ProjectDTO getByProjectCode(String projectCode) {
        return null;
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> projectList = projectRepository.findAll(Sort.by("projectCode"));
        return projectList.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
       dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

    }

    @Override
    public void delete(String projectCode) {

    }
}
