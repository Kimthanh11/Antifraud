package antifraud.entity;

import antifraud.service.UserService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private UserService.UserRole  role = UserService.UserRole.MERCHANT;

    private boolean locked = true;

    public User() {}



    public User(String name, String username, String encodedPassword) {
        this.name = name;
        this.username = username;
        this.password = encodedPassword;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserService.UserRole getRole() {
        return role;
    }

    public void setRole(UserService.UserRole role) {
        this.role = role;
    }

    public boolean getLocked() { return locked;}

    public void setLocked(boolean locked) {this.locked = locked;}

}