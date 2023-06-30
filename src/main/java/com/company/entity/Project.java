package com.company.entity;

import com.company.enums.Status;
import com.company.entity.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity {

    @Column(unique = true)
    private String projectCode;
    @NotBlank(message = "Please enter a project name")
    private String projectName;
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    private String projectDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User assignedManager;


}
