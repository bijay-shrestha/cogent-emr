package com.cogent.authservice.service;

import com.cogent.authservice.repository.AdminRepository;
import com.cogent.authservice.repository.RoleRepository;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service ("cogentUserDetailsService")
@Slf4j
public class CogentUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    private AdminRepository adminRepository;
    private RoleRepository roleRepository;

    public CogentUserDetailsService(AdminRepository adminRepository,
                                    RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Admin> admins = adminRepository.findAll();

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {

                Collection<? extends GrantedAuthority> grantedAuthorities = getAuthorities(admin);
                return new User(admin.getUsername(), admin.getPassword(), grantedAuthorities);
            }

        }
        throw new UsernameNotFoundException("Username: " + username + " not found.");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Admin admin) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<Roles> roles = roleRepository.findRolesByAdminId(admin.getId());

        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }

        log.info(" ::::: CogentUserDetailsService.class ----- assigned roles {}", authorities);

        return authorities;
    }
}
