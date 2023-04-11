package org.excercise.pizzeria.springlamiapizzeriacrud.security;

import org.excercise.pizzeria.springlamiapizzeriacrud.model.Role;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    //ATTRIBUTES to fill USERDETAILS'S METHODS
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    //CONSTRUCTOR in which to fill the autorithies Set
    public DatabaseUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new HashSet<>();
        for (Role r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
    }

    //USERDETAILS'S METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}
