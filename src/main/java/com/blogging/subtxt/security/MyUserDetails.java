package com.blogging.subtxt.security;

import com.blogging.subtxt.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class MyUserDetails implements UserDetails {
    private final User user;

    public MyUserDetails(User user) {

        this.user = user;
    }

    public String getAppUsername() {
        return user.getUsername();
    }
    public Integer getUserId() {
        return user.getId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return user.getEmail();       // authentication done by email only as token subject
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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