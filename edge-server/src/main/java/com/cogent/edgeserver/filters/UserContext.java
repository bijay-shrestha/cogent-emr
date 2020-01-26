package com.cogent.edgeserver.filters;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String USERNAME = "username";

    private String username = new String();

    public String getUsername() { return username;}
    public void setUsername(String username) {
        this.username = username;
    }


}