package com.company.service.impl;

import com.company.dto.RoleDTO;
import com.company.entity.Role;
import com.company.mapper.RoleMapper;
import com.company.repository.RoleRepository;

import com.company.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public List<RoleDTO> findAll() {
        // go to DB and get all roles

        List<Role> roleList = roleRepository.findAll();

        return roleList.stream().map(roleMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
       return  roleMapper.convertToDTO(roleRepository.findById(id).get());
       //roleRepository.findById(id).map(roleMapper::convertToDTO).get(); // roleMapper.convertToDTO(roleRepository.findById(id).get());

    }
}
