package com.test.OrionTek.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.OrionTek.user.role.Role;
import com.test.OrionTek.user.role.RoleRepository;

@Service
public class UserService implements DefaultUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
	     return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

    @Override
    public User save(UserDTO user) {
        Role role = new Role();
        
        try {
            role = roleRepository.findByRole(user.getRole()).orElseThrow(()-> new RoleNotFoundException("User role not found"));
        } catch (RoleNotFoundException e) { e.printStackTrace(); }
    
        User newUser = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).email(user.getEmail()).roles(new HashSet<Role>()).build();
        newUser.setRole(role);
        return userRepository.save(newUser);
    }


    @Override
    public User delete(UserDTO user) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
