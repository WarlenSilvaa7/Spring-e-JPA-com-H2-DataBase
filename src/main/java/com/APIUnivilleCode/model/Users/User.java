package com.APIUnivilleCode.model.Users;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonGetter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "users")
@Table(name = "users")

public class User implements UserDetails{
    
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "nome_user")
    private String nome_user;

    @Column(name = "password_user")
    private String password_user;

    @Column(name = "role")
    private UserRole role_user;

    public UserRole getRole_user() {
        return role_user;
    }
    public void setRole_user(UserRole role_user) {
        this.role_user = role_user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome_user() {
        return nome_user;
    }
    public void setNome_user(String nome_user) {
        this.nome_user = nome_user;
    }
    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role_user == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_User"));
    }
    @Override
    public String getUsername() {
        return nome_user;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return password_user;
    }
 
}
