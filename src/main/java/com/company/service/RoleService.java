package com.company.service;

import com.company.dto.RoleDTO;
import com.company.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAll();
    RoleDTO findById(Long id); //Derived


}
