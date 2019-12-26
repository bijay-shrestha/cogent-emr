package com.cogent.contextserver.config;

import com.cogent.contextserver.filter.UserContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("auditable point ----------------------" + UserContext.getUsername());
        return Optional.of(UserContext.getUsername());
    }

}

//public class AuditorAwareImpl implements AuditorAware<String> {
//    public Optional<String> getCurrentAuditor() {
//
//        String userName=UserContext.getUsername();
//        if(userName==null){
//            return null;
//        }
//        System.out.println("user ame in auditable is------------"+userName);
//
//        return Optional.of(userName);
////        Authentication authentication =
////                SecurityContextHolder.getContext().getAuthentication();
////        if (authentication == null || !authentication.isAuthenticated()) {
////            return
////                    null;
////        }
////        return ((MyUserDetails) authentication.getPrincipal()).getUser();
//
//
//    }
//}
