package com.company.service.impl;

import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAll() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserName(username);
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
        User user = userRepository.findByUserName(dto.getUserName());  //has id
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
        User user = userRepository.findByUserName(username);
        // change the isDeleted field to true
        user.setIsDeleted(true);
        // save the user in the db
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCase(role);
        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }


}