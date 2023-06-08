package com.company.mapper;

import com.company.dto.RoleDTO;
import com.company.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO roleDTO){
        return modelMapper.map(roleDTO, Role.class);
    }
    public RoleDTO convertToDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }

}
