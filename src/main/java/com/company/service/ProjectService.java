package com.company.service;

import com.company.dto.ProjectDTO;
import com.company.dto.UserDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO getByProjectCode(String projectCode);

    List<ProjectDTO> findAll();

    void save(ProjectDTO dto);

    void update(ProjectDTO dto);

    void delete(String projectCode);

    void complete(String projectCode);


    List<ProjectDTO> listAllProjectDetails();

    List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO userDTO);
}
