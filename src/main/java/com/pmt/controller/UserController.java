package com.pmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmt.entity.User;
import com.pmt.payload.JwtLoginResponse;
import com.pmt.payload.LoginRequest;
import com.pmt.security.JwtUtilHelper;
import com.pmt.service.CustomUserDetailsService;
import com.pmt.service.MapValidationExceptionService;
import com.pmt.service.UserService;
import com.pmt.validator.UserValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationExceptionService exceptionService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	//login
	@PostMapping("/login")
	public ResponseEntity<?> getLogin(@Valid @RequestBody LoginRequest loginRequest,BindingResult bindingResult){
		ResponseEntity<Object> handleMapValidationExceptionService = this.exceptionService.handleMapValidationExceptionService(bindingResult);
		if(handleMapValidationExceptionService!=null) return handleMapValidationExceptionService;
		this.authenticate(loginRequest.getUsername(),loginRequest.getPassword());
		String generateToken = this.jwtUtilHelper.generateToken(this.customUserDetailsService.loadUserByUsername(loginRequest.getUsername()));
		
		JwtLoginResponse jwtLoginResponse =new JwtLoginResponse(generateToken, true);
		return ResponseEntity.ok(jwtLoginResponse);
	
	}
	
	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(username, password);
		this.authenticationManager.authenticate(authenticationToken);
		
	}

	//signup
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult bindingResult){
		//validate password match
		this.userValidator.validate(user, bindingResult);
		ResponseEntity<?> handleMapValidationExceptionService = this.exceptionService.handleMapValidationExceptionService(bindingResult);
		if(handleMapValidationExceptionService != null) return handleMapValidationExceptionService;
		User saveUser = this.userService.saveUser(user);
		return new ResponseEntity<User>(saveUser,HttpStatus.CREATED);
	}
}
