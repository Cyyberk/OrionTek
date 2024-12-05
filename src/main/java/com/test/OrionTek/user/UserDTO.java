package com.test.OrionTek.user;

import com.test.OrionTek.user.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
}
