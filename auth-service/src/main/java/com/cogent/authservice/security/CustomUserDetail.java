package com.cogent.authservice.security;

import com.cogent.persistence.model.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CustomUserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    Set<GrantedAuthority> authorities=null;

    public void setAuthorities(Set<GrantedAuthority> authorities){
        this.authorities=authorities;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
