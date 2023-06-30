package com.company.repository;

import com.company.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus != 'COMPLETE'")
    int totalNonCompletedTasks(String projectCode);
    @Query(nativeQuery = true, value = "select count(*) from task t join project p " +
            "on p.id = t.project_id where p.project_code = ?1 and t.task_status = 'COMPLETE'")
    int totalCompletedTasks(String projectCode);
}
