package com.company.repository;

import com.company.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //give me the role based on description
    Role findByDescription(String description);
}

