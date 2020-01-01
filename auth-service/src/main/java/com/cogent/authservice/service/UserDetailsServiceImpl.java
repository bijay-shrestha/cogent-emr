package com.cogent.authservice.service;

import com.cogent.authservice.repository.AdminRepository;
import com.cogent.authservice.repository.ApplicationModuleRepository;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ApplicationModule;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private AdminRepository adminRepository;
    private ApplicationModuleRepository applicationModuleRepository;

    public UserDetailsServiceImpl(AdminRepository adminRepository,
                                  ApplicationModuleRepository applicationModuleRepository) {
        this.adminRepository = adminRepository;
        this.applicationModuleRepository = applicationModuleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Admin> admins = adminRepository.findAll();

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {

                Collection<? extends GrantedAuthority> grantedAuthorities = getAuthorities(admin);
//                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//                        .commaSeparatedStringToAuthorityList("ROLE_" + admin.getRoles());
                return new User(admin.getUsername(), admin.getPassword(), grantedAuthorities);
            }

        }
        throw new UsernameNotFoundException("Username: " + username + " not found.");
    }

    /*APPLICATION MODULE CODE EQUALS SUB-DEPARTMENT CODE*/
    private Collection<? extends GrantedAuthority> getAuthorities(Admin admin) {

        List<GrantedAuthority> list = new ArrayList<>();
        List<ApplicationModule> applicationModules =
                applicationModuleRepository.findApplicationModuleByAdminId(admin.getId());

        for (ApplicationModule applicationModule : applicationModules) {
            list.add(new SimpleGrantedAuthority("ROLE_" + applicationModule.getSubDepartmentId().getCode()));
        }

        return list;
    }
}
