package com.pmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmt.entity.User;
import com.pmt.repository.UserRepository;
@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		if(user == null)  new UsernameNotFoundException("User Not Found="+username);
		return user;
	}
	
	@Transactional
	public User loadUserById(Long id) {
		User user = this.userRepository.getById(id);
		if(user == null) new UsernameNotFoundException("User not found");
		return user;
	}
}
