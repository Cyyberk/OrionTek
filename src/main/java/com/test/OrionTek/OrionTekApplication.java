package com.test.OrionTek;

import javax.management.relation.RoleNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrionTekApplication {

	public static void main(String[] args) throws RoleNotFoundException {
		SpringApplication.run(OrionTekApplication.class, args);
	}

}
