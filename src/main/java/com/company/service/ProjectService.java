package com.company.service;

import com.company.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO getByProjectCode(String projectCode);

    List<ProjectDTO> findAll();

    void save(ProjectDTO dto);

    void update(ProjectDTO dto);

    void delete(String projectCode);

}
