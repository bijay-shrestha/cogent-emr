//package com.cogent.contextserver.filter;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserContext {
//    public static final String USERNAME = "username";
//
//    private static final ThreadLocal<String> usernameThread = new ThreadLocal<String>();
//
//    public static String getUsername() { return usernameThread.get(); }
//    public static void setUsername(String arg) {
//        usernameThread.set(arg);}
//
//
//}