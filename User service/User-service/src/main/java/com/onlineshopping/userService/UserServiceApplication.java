package com.onlineshopping.userService;

import com.onlineshopping.userService.entities.ERole;
import com.onlineshopping.userService.entities.Role;
import com.onlineshopping.userService.entities.User;
import com.onlineshopping.userService.repository.RoleRepository;
import com.onlineshopping.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Add default roles if they don't already exist
		addRoleIfNotExists(ERole.ROLE_ADMIN);
		addRoleIfNotExists(ERole.ROLE_MODERATOR);
		addRoleIfNotExists(ERole.ROLE_MODERATOR);

		System.out.println("Default roles created or verified.");

		if (userRepository.findByUsername("admin").isEmpty()) {
			// Create a new user
			User defaultUser = new User();
			defaultUser.setUsername("admin123@gmail.com");
			defaultUser.setPassword(new BCryptPasswordEncoder().encode("admin123")); // Use encryption for passwords
			// Assign roles to the user
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());
			defaultUser.setRoles(roles);
			// Save the user to the database
			userRepository.save(defaultUser);

			System.out.println("Default admin user created successfully.");
		}
	}
	private void addRoleIfNotExists(ERole roleName) {
		// Check if the role exists in the database
		if (roleRepository.findByName(roleName).isEmpty()) {
			Role role = new Role();
			role.setName(roleName);
			roleRepository.save(role);
			System.out.println(roleName + " role created.");
		}
	}
}
