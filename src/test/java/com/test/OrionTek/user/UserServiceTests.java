package com.test.OrionTek.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.test.OrionTek.user.role.Role;
import com.test.OrionTek.user.role.RoleRepository;

@SpringBootTest
class UserServiceTests {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	BCryptPasswordEncoder passwordEncoder;

	private User mockUser;
	private Role mockRole;
	
	public UserServiceTests(){
		mockUser = new User("jean18699", "abc", "jean@gmail.com");
		mockRole = new Role("USER");
		mockUser.setId(1);
		mockUser.setRole(mockRole);
	}

	@BeforeEach
	void init(){
		when(roleRepository.findByRole("USER")).thenReturn(Optional.of(mockRole));
	}

	@Test
	@DisplayName("Test 1: Register User Test")
	@Order(1)
	void registerUserTest(){
		UserDTO userDTO = new UserDTO("jean18699", "abc", "jean@gmail.com", mockRole.getRole());

		when(passwordEncoder.encode(any(String.class))).thenReturn(userDTO.getPassword());
		when(userRepository.save(any((User.class)))).thenReturn(mockUser);

		// Calling the method save.
		User result = userService.save(userDTO);
		
		// Verifying the final result is the same as the input when created the new user
		assertNotNull(result);
		assertEquals(userDTO.getUsername(), result.getUsername());
		assertEquals("abc", result.getPassword());
		assertEquals(userDTO.getEmail(), result.getEmail());
		System.out.println(result.getRoles().size());
		Assertions.assertThat(result.getRoles().contains(mockRole)).isTrue();	

	}
	
	@Test
	@DisplayName(("Test 2: Find user by username"))
	@Order(2)
	void findUserByUsername(){
		String mockUsername = "jean18699";
		when(userRepository.findByUsername(mockUsername)).thenReturn(Optional.of(mockUser));
		
		UserDetails result = userService.loadUserByUsername(mockUsername);
		assertNotNull(result);
		assertEquals(mockUsername, result.getUsername());
	}

	@Test
	void contextLoads() {
	}

}
