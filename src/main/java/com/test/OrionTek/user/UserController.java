package com.test.OrionTek.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import com.test.OrionTek.security.jwt.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "${api.version}")
@Tag(name = "User Controller", description = "Operations related with the system user credentials and validation")
public class UserController {
    
    UserRepository userRepository;
    AuthenticationManager authManager;
    JwtUtil jwtUtil;
    BCryptPasswordEncoder passwordEncoder;
    UserService userService;

    public UserController(UserRepository userRepository, AuthenticationManager authManager, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder, UserService userService){
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

     // Documentation for swagger-ui. Check them by accesing: localhost:port/swagger-ui/index.html#/
    @Operation(summary = "Register New User", 
    description = "This endpoint will register a new user of the system. This registered user can be used in the login endpoint, in order to generate a JWT token that must be passed as a bearer token to allow further requests",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO user) {
        User newuser = userService.save(user);
        if(newuser == null){
            return generateRespose("Unable to create user", HttpStatus.BAD_REQUEST, newuser);
        }
        return generateRespose("User saved successfully!", HttpStatus.CREATED, newuser);
    }
    
    
    @Operation(summary = "Login User", 
    description = "This endpoint will login an user and provide a JWT token. The generated token must be passed as a bearer token in each request to allow operations",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "User logged!");
		map.put("token", generateJwtToken(user));
		return new ResponseEntity<Object>(map, HttpStatus.ACCEPTED);    
    }
    

    @Operation(summary = "Testing Purpose Only", description = "Will greet the user only if it has the role of a admin")

    @GetMapping("/welcomeAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String welcome() {
		return "WelcomeAdmin";
	}

    
    @Operation(summary = "Testing Purpose Only", description = "Will greet the user only if it has the role of a normal user")
	
    @GetMapping("/welcomeUser")
	@PreAuthorize("hasAuthority('USER')")
	public String welcomeUser() {
		return "WelcomeUser";
	}

    /** Utility method to generate a flexible response */
    private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}

    /** Method to create a jwt token */
	public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtUtil.generateToken(authentication);
	}


}
