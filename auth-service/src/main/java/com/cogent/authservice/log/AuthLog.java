package com.cogent.authservice.log;

/**
 * @author smriti ON 02/01/2020
 */
public class AuthLog {

    public static final String AUTHENTICATION_PROCESS_STARTED = "::: ATTEMPT AUTHENTICATION PROCESS STARTED :::";
    public static final String AUTHENTICATION_PROCESS_COMPLETED = "::: {} AUTHENTICATED SUCCESSFULLY WITH JWT TOKEN: {}:::";

    public static final String HEADER = "::: HEADER : {} :::";
    public static final String PREFIX = "::: PREFIX : {} :::";
    public static final String SECRET = "::: SECRET : {} :::";
    public static final String EXPIRATION_TIME = "::: EXPIRATION TIME : {} :::";
}
