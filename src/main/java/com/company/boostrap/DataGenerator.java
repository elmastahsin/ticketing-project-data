package com.company.boostrap;

import com.company.dto.ProjectDTO;
import com.company.dto.RoleDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.enums.Gender;
import com.company.enums.Status;
import com.company.service.ProjectService;
import com.company.service.RoleService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataGenerator implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public DataGenerator(RoleService roleService, UserService userService, ProjectService projectService, TaskService taskService) {
        this.roleService = roleService;
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {

        RoleDTO adminRole = new RoleDTO(1L,"Admin");
        RoleDTO managerRole = new RoleDTO(2L,"Manager");
        RoleDTO employeeRole = new RoleDTO(3L,"Employee");

        roleService.save(adminRole);
        roleService.save(managerRole);
        roleService.save(employeeRole);

        UserDTO user1 = new UserDTO("John", "Kesy",
                "john@metu.com", "Abc1", "Abc1", true, "7459684532", managerRole, Gender.MALE);
        UserDTO user2 = new UserDTO("Mike", "Smith",
                "mike@metu.com", "Abc2", "Abc2", true, "7459684532", adminRole, Gender.MALE);
        UserDTO user3 = new UserDTO("Delisa", "Norre",
                "delisa@metu.com",  "Abc3", "Abc3", true, "8567412358", managerRole, Gender.FEMALE);
        UserDTO user4 = new UserDTO("Craig", "Jark",
                "craig@metu.com", "Abc4", "Abc4", true, "7777775566", employeeRole, Gender.MALE);
        UserDTO user5 = new UserDTO("Shaun", "Hayns",
                "shaun@metu.com", "Abc5", "Abc5", true, "3256987412", managerRole, Gender.MALE);
        UserDTO user6 = new UserDTO("Elizebeth", "Loren",
                "elizebeth@metu.com", "Abc6", "Abc6", true, "5306987412", employeeRole, Gender.FEMALE);
        UserDTO user7 = new UserDTO("Maria", "Ada",
                "maria@metu.com", "Abc7", "Abc7", true, "9996987412", employeeRole, Gender.FEMALE);
        UserDTO user8 = new UserDTO("Bill", "Matt",
                "bill@metu.com", "Abc8", "Abc8", true, "8881239846", employeeRole, Gender.MALE);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);
        userService.save(user5);
        userService.save(user6);
        userService.save(user7);
        userService.save(user8);

        ProjectDTO project1 = new ProjectDTO("Spring MVC", "PR001", user1, LocalDate.now(), LocalDate.now().plusDays(25), "Creating Controllers", Status.OPEN);
        ProjectDTO project2 = new ProjectDTO("Spring ORM", "PR002", user2, LocalDate.now(), LocalDate.now().plusDays(10), "Creating Database", Status.IN_PROGRESS);
        ProjectDTO project3 = new ProjectDTO("Spring Container", "PR003", user1, LocalDate.now(), LocalDate.now().plusDays(32), "Creating Container", Status.IN_PROGRESS);

        projectService.save(project1);
        projectService.save(project2);
        projectService.save(project3);

        TaskDTO task1 = new TaskDTO(1L,project1, user8, "Controller", "Request Mapping", Status.IN_PROGRESS, LocalDate.now().minusDays(4));
        TaskDTO task2 = new TaskDTO(2L,project3, user3, "Configuration", "Database Connection", Status.COMPLETE, LocalDate.now().minusDays(12));
        TaskDTO task3 = new TaskDTO(3L,project3, user6, "Mapping", "One-To-Many", Status.COMPLETE, LocalDate.now().minusDays(8));
        TaskDTO task4 = new TaskDTO(4L,project2, user7, "Dependency Injection", "Autowired", Status.IN_PROGRESS, LocalDate.now().minusDays(20));

        taskService.save(task1);
        taskService.save(task2);
        taskService.save(task3);
        taskService.save(task4);

    }

}
