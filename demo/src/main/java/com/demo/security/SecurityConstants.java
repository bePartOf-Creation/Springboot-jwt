package com.demo.security;

public class SecurityConstants {
    public static final String SECRET = "S3CR3TT0K3YJWT";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String VIEW_ALL_URL = "/user/**";
    public static final String SIGN_UP_URL = "/user/signUp/**";
    public static final String UPDATE_URL = "/user/update/**";
}