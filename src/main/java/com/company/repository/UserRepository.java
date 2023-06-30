package com.company.repository;

import com.company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    @Transactional
    void deleteByUserName(String username);


    List<User> findByRoleDescriptionIgnoreCase(String description);

}


