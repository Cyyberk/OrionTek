package com.test.OrionTek.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;


	@BeforeEach
	void init(){
		
	}

	@Test
	@DisplayName("Test 1: Save User Test")
	@Order(1)
	void saveUserTest(){
		User mockUser = new User("jean18699", "abc", "jean@gmail.com");
		userRepository.save(mockUser);
		Assertions.assertThat(mockUser.getId()).isGreaterThan(0);
	}
	
	@Test
	@DisplayName(("Test 2: Find user"))
	@Order(2)
	void findUserTest(){
		Optional<User> userById = userRepository.findById(1L);
		Optional<User> userByUsername = userRepository.findByUsername("jean18699");
		assertNotNull(userById);
		assertNotNull(userByUsername);
		assertEquals(userById, userByUsername);
	}

	@Test
	@DisplayName(("Test 3: Delete user"))
	@Order(3)
	void findAndDeleteUserTest(){
		userRepository.deleteById(1L);
		Optional<User> user = userRepository.findById(1L);
		Assertions.assertThat(user).isEmpty();
	}

	

	@Test
	void contextLoads() {
	}

}
