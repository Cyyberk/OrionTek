package com.test.OrionTek.user;

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
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import com.test.OrionTek.user.role.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    private String username;
    private String password;
    private String email;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
	private Set<Role> roles = new HashSet<>();

    public User(String username, String password, String email){
        this.username = username; this.password = password; this.email = email;
    }


    public void addRole(Role role){
        this.roles.add(role);
    }

}