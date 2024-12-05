package com.test.OrionTek;

import javax.management.relation.RoleNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.OrionTek.user.UserDTO;
import com.test.OrionTek.user.UserService;
import com.test.OrionTek.user.role.RoleRepository;

@SpringBootApplication
public class OrionTekApplication {

	static UserService userService;
	static RoleRepository roleRepository;

	public OrionTekApplication(UserService userService, RoleRepository roleRepository){
		OrionTekApplication.userService = userService;
		OrionTekApplication.roleRepository = roleRepository;
	}

	public static void main(String[] args) throws RoleNotFoundException {
		SpringApplication.run(OrionTekApplication.class, args);

		// Creating the Admin
		UserDTO admin = new UserDTO("jean18699","abc","jean18699@gmail.com", "ADMIN");
		userService.save(admin);
	}

}
