package com.pmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pmt.entity.User;
import com.pmt.exceptions.UsernameAlreadyExistException;
import com.pmt.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public User saveUser(User user) {
		try {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setUsername(user.getUsername());
		//user has to be unique
		user.setConfirmPassword("");
		//make sure that password and confirmPassword match
		
		//we don't persist or show the confirm password
		
		return this.userRepository.save(user);
		}catch(Exception e) {
			throw new UsernameAlreadyExistException("Username '"+user.getUsername()+"' is already exist");
		}
	}
}
