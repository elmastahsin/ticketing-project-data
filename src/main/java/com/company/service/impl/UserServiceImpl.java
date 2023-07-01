package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.ProjectService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> findAll() {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public UserDTO update(UserDTO dto) {
        //Find current user
        User user = userRepository.findByUserNameAndIsDeleted(dto.getUserName(),false);  //has id
        //Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto);   // has id?
        //set id to the converted object
        convertedUser.setId(user.getId());
        //save the updated user in the db
        userRepository.save(convertedUser);
        return findByUserName(dto.getUserName());
    }

    //delete
    @Override
    public void delete(String username) {
        // go to db and get that user by username
        User user = userRepository.findByUserNameAndIsDeleted(username,false);
        // change the isDeleted field to true
        if (checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());
            // save the user in the db
            userRepository.save(user);
        }

    }

    @Override
    public List<UserDTO> findAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);
        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user){
        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projects = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projects.size() == 0;
            case "Employee":
                List<TaskDTO> tasks = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return tasks.size() == 0;
            default:
                return false;
        }
    }

}