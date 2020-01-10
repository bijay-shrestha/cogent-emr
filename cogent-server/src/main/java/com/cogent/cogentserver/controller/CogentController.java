package com.cogent.cogentserver.controller;

import com.cogent.adminservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Slf4j
public class CogentController {

    @GetMapping("/test")
    public User cogent() {
        log.info("::::: COGENT SERVER IS RUNNING :::::: ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("User is not authenticated; Found " + authentication.getPrincipal() + " of type " + authentication.getPrincipal().getClass() + "; Expected type User");
        }

    }
    
}
