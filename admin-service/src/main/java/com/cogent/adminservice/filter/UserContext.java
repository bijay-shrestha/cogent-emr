package com.cogent.adminservice.filter;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String USERNAME = "username";

    public static String username = "";

    public static String getUsername() { return username;}
    public void setUsername(String username) {
        this.username = username;
    }


}