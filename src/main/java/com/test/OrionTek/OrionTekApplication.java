package com.test.OrionTek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.OrionTek.user.role.Role;
import com.test.OrionTek.user.role.RoleRepository;

@SpringBootApplication
public class OrionTekApplication {

	static RoleRepository roleRepository;

	public OrionTekApplication(RoleRepository roleRepository){
		OrionTekApplication.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(OrionTekApplication.class, args);

		// Role roleAdmin = new Role(); roleAdmin.setRole("ADMIN");
		// Role roleUser = new Role(); roleUser.setRole("USER");

		// roleRepository.save(roleAdmin);
		// roleRepository.save(roleUser);

	}

}
