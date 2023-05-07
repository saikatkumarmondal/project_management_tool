package com.pmt.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.pmt.exceptions.InvalidLoginResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	InvalidLoginResponse invalidLoginResponse =new InvalidLoginResponse();
	String json = new Gson().toJson(invalidLoginResponse);
	response.setContentType("application/json");
	response.setStatus(401);
	response.getWriter().print(invalidLoginResponse);
	}

}
