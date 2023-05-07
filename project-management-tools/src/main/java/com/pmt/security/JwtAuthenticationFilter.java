package com.pmt.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pmt.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
		
			//1. get the token
			
			String requestHeader = request.getHeader("Authorization");
		
		//Bearer ttyuty6765
			
		String username=null;
		String jwtToken=null;
		
		if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
			jwtToken=requestHeader.substring(7);
			
		try {
			username=this.jwtUtilHelper.getUsernameFromToken(jwtToken);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (MalformedJwtException e) {
		e.printStackTrace();
		}catch (ExpiredJwtException e) {
			e.printStackTrace();
		}
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		//once we got the token,now validate
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			if(this.jwtUtilHelper.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//authentication
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
				
			
			}
			
		}
		
		}
		
		filterChain.doFilter(request, response);
		
	}

}
