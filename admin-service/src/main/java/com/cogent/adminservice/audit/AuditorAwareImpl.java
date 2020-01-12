package com.cogent.adminservice.audit;

import com.cogent.persistence.model.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//		String username;
//		Object principal =
//				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof CustomUserDetail) {
//			username = ((Admin)principal).getUsername();
//		} else {
//			username = principal.toString();
//		}
//
//		log.info("username :: is my myaan{}", username);
//
//		// Can use Spring Security to return currently logged in user
//		return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		return Optional.of("bijay");
    }


}