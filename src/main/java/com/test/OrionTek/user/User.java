package com.test.OrionTek.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.test.OrionTek.user.role.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "Users")
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private String username;
    private String password;
    private String email;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
	inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role") )
	private Set<Role> roles;

    public void setRole(Role role){
        this.roles.add(role);
    }

}