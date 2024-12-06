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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
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

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO user) {
        User newuser = userService.save(user);
        if(newuser == null){
            return generateRespose("Unable to create user", HttpStatus.BAD_REQUEST, newuser);
        }
        return generateRespose("User saved successfully!", HttpStatus.CREATED, newuser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO user) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "User logged!");
		map.put("token", generateJwtToken(user));
		return new ResponseEntity<Object>(map, HttpStatus.ACCEPTED);
        
    }
    

    @GetMapping("/welcomeAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String welcome() {
		return "WelcomeAdmin";
	}

	@GetMapping("/welcomeUser")
	@PreAuthorize("hasAuthority('USER')")
	public String welcomeUser() {
		return "WelcomeUser";
	}

    private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}

	public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtUtil.generateToken(authentication);
	}


}
