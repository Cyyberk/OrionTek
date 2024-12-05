package com.test.OrionTek.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService{

    public User save(UserDTO user);
    public User delete(UserDTO user);
    
}
