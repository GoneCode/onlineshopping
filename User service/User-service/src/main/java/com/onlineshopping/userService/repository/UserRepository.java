package com.onlineshopping.userService.repository;

import com.onlineshopping.userService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {



    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
