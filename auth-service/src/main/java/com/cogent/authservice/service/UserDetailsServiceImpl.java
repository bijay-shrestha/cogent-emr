package com.cogent.authservice.service;

import com.cogent.authservice.repository.AdminRepository;
import com.cogent.authservice.repository.ApplicationModuleRepository;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.ApplicationModule;
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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    private AdminRepository adminRepository;
    private ApplicationModuleRepository applicationModuleRepository;

    public UserDetailsServiceImpl(AdminRepository adminRepository,
                                  ApplicationModuleRepository applicationModuleRepository) {
        this.adminRepository = adminRepository;
        this.applicationModuleRepository = applicationModuleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//                final List<Admin> admins = Arrays.asList(
//                new Admin(1L, "bijay", encoder.encode("1234"), "GAL"),
//                new Admin(2L, "pharmacy", encoder.encode("pharmacy"), "PHA"),
//                new Admin(2L, "bishow", encoder.encode("12345"), "IMG"),
//                new Admin(2L, "account", encoder.encode("account"), "ACC")
//
//        );

        /* for modules*/
//        final List<Admin> admins = Arrays.asList(
//                new Admin(1L, "bijay", encoder.encode("12345678"), RoleList.roleList1()),
//                new Admin(2L, "account", encoder.encode("account"), RoleList.roleList2())
//
//        );

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

    private Collection<? extends GrantedAuthority> getAuthorities(Admin admin) {

        List<GrantedAuthority> list = new ArrayList<>();
        List<ApplicationModule> applicationModules =
                applicationModuleRepository.findApplicationModuleByAdminId(admin.getId());

        for (ApplicationModule applicationModule : applicationModules) {
            list.add(new SimpleGrantedAuthority("ROLE_" + applicationModule.getCode()));
        }

        return list;
    }
}
