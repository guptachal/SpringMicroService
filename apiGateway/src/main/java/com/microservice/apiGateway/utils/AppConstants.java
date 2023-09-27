package com.microservice.apiGateway.utils;

public class AppConstants {
    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String  JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMIN = "User Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You don't have the permission to access the page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login","/user/register"};
    public static final String[] PROTECTED_COMMON_URLS = {"/user/update","/user/update/roles","/user/update/**","/user/home"};
    public static final String[] ADMIN_ACCESS_URLS = {"/user/admin"};
    public static final String[] USER_ACCESS_URLS = {"/user/user"};
}