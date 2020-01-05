package com.cogent.contextserver.config;

import com.cogent.contextserver.filter.UserContext;
import com.cogent.contextserver.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        return Optional.of("bijay");
        String username;
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((User)principal).getFirstName();
        } else {
            username = principal.toString();
        }

        log.info("username :: is my myaan{}", username);

        // Can use Spring Security to return currently logged in user
        return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

}