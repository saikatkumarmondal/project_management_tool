package com.pmt.security;

public class SecurityConstant {
	
	public final static String SIGNUP_URL="/api/users/**";
	public final static String SECRET="SecretKeyToGen";
	public final static String TOKEN_PREFIX="Bearer ";
	public final static String HEADER_STRING="Authorization";
	public final static long EXPRATION_TIME=30_000;
}
