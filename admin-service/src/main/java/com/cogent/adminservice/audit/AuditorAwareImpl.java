package com.cogent.adminservice.audit;

import com.cogent.adminservice.filter.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@SuppressWarnings("ALL")
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("CURRENT AUDITOR :: {}", UserContext.getUsername());
		return Optional.of(UserContext.getUsername());
    }

}