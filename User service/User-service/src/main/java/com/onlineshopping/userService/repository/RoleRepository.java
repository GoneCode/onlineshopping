package com.onlineshopping.userService.repository;

import com.onlineshopping.userService.entities.ERole;
import com.onlineshopping.userService.entities.Role;
import com.onlineshopping.userService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(ERole name);

}
