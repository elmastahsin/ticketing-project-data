package com.company.service;


import com.company.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findByUserName(String username);
    void deleteByUserName(String username);
    void save(UserDTO user);
    UserDTO update(UserDTO user);
    void delete(String username);


    List<UserDTO> findAllByRole(String Role);
}
