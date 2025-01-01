package com.onlineshopping.userService.repository;

import com.onlineshopping.userService.entities.ERole;
import com.onlineshopping.userService.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(ERole name);

}
