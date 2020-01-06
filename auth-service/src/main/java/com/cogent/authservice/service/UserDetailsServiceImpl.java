package com.cogent.authservice.service;

import com.cogent.authservice.feign.dto.response.AdminInfoByUsernameResponseDTO;
import com.cogent.authservice.feign.service.AdminService;
import com.cogent.genericservice.exception.NoContentFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cogent.authservice.constants.ErrorMessageConstants.USERNAME_NOT_FOUND;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminService adminService;

    public UserDetailsServiceImpl(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminInfoByUsernameResponseDTO responseDTO = adminService.fetchAdminInfoByUsernameResponseDTO(username)
                .orElseThrow(() -> new NoContentFoundException(String.format(USERNAME_NOT_FOUND, username)));

        List<GrantedAuthority> grantedAuthorities = responseDTO.getAssignedApplicationModuleCodes()
                .stream().map(code -> new SimpleGrantedAuthority("ROLE_" + code))
                .collect(Collectors.toList());

        return new User(username, responseDTO.getPassword(), grantedAuthorities);
    }
}
