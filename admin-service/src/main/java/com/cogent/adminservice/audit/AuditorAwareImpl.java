package com.cogent.adminservice.audit;

import com.cogent.adminservice.filter.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("AUDITABLE POINT :: {}", UserContext.getUsername());
//		String username;
//		Object principal =
//				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof UserDetails) {
//			username = ((UserDetails)principal).getUsername();
//		} else {
//			username = principal.toString();
//		}
//		return Optional.of(username);
		return Optional.of(UserContext.getUsername());
    }


}