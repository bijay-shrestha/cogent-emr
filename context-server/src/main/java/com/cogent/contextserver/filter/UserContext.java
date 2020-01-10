package com.cogent.contextserver.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
public class UserContext {

    private static final ThreadLocal<String> username = new ThreadLocal<String>();

    public static String getUsername() { return username.get(); }
    public static void  setUsername(String arg) {
        username.set(arg);}


}
