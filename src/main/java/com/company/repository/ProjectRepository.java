package com.company.repository;

import com.company.entity.Project;
import com.company.entity.User;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Project findByProjectCode(String projectCode);

    List<Project> findAllByAssignedManager(User manager);

    List<Project> findAllByAssignedManagerAndProjectStatusIsNot(User user, Status status);
}
