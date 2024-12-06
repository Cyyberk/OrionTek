package com.test.OrionTek.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.test.OrionTek.security.jwt.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	UserService userService;

	@Mock
	AuthenticationManager authManager;

	@BeforeEach
	void init(){
		
	}


	@Test
	@DisplayName("Test 1: Check secure endpoint with unauthorized user")
	@Order(1)
	void shouldForbideUnauthorizedUsers() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeAdmin"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());

		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeUser"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@DisplayName("Test 2: Check authorized endpoints with authorized user, but with role User")
	@Order(2)
	@WithMockUser(authorities = "USER")
	void shouldGreetAuthorizedUserIfNotAdmin() throws Exception{

		// If not admin, user can't access admin's endpoint. Must return forbidden
		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeAdmin"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());

		// If normal user, user can access normal user endpoints. Must return OK
		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeUser"))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@DisplayName("Test 3: Check authorized endpoints with authorized user, but with role Admin")
	@Order(3)
	@WithMockUser(authorities = "ADMIN")
	void shouldGreetAuthorizedUserIfAdmin() throws Exception{

		// If not admin, user can't access admin's endpoint. Must return forbidden
		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeAdmin"))
		.andExpect(MockMvcResultMatchers.status().isOk());

		// If normal user, user can access normal user endpoints. Must return OK
		mockMvc.perform(MockMvcRequestBuilders.get("/welcomeUser"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());

	}
	

	

	@Test
	void contextLoads() {
	}

}
